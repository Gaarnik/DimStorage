package com.gaarnik.dimstorage.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.event.world.WorldEvent.Unload;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DimStorageManager {
	// ****************************************************************

	// ****************************************************************
	private static DimStorageManager clientManager;
	private static DimStorageManager serverManager;

	private static HashMap<String, IDimStoragePlugin> plugins = new HashMap<String, IDimStoragePlugin>();

	// ****************************************************************
	private Map<String, AbstractDimStorage> storageMap;
	private Map<String, List<AbstractDimStorage>> storageList;
	private final boolean client;

	private File saveDir;
	private File[] saveFiles;
	private int saveTo;
	private List<AbstractDimStorage> dirtyStorage;
	private NBTTagCompound saveTag;

	// ****************************************************************
	public DimStorageManager(boolean client, World world) {
		this.client = client;

		this.storageMap = Collections.synchronizedMap(new HashMap<String, AbstractDimStorage>());
		this.storageList = Collections.synchronizedMap(new HashMap<String, List<AbstractDimStorage>>());
		this.dirtyStorage = Collections.synchronizedList(new LinkedList<AbstractDimStorage>());

		for(String key : plugins.keySet())
			this.storageList.put(key, new ArrayList<AbstractDimStorage>());

		if(client == false)
			this.load(world);
	}

	// ****************************************************************
	public AbstractDimStorage getStorage(String owner, int freq, String type) {
		if(owner == null)
			owner = "public";
		
		// used for debug
		/*System.out.println("-------------");
		System.out.println("getStorage for " + (this.client ? "client": "server"));
		System.out.println("-------------");
		System.out.println("owner: " + owner);
		System.out.println("freq: " + freq);
		System.out.println("type: " + type);*/

		String key = owner+"|"+freq+"|"+type;
		AbstractDimStorage storage = this.storageMap.get(key);

		if(storage == null) {
			storage = plugins.get(type).createStorage(this, owner, freq);

			if(!this.client && this.saveTag.hasKey(key))
				storage.loadFromTag(this.saveTag.getCompoundTag(key));

			this.storageMap.put(key, storage);
			this.storageList.get(type).add(storage);
		}

		return storage;
	}

	private void load(World world) {
		this.saveDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "DimStorage");

		try {
			if(!this.saveDir.exists())
				this.saveDir.mkdirs();

			this.saveFiles = new File[]{new File(saveDir, "data1.dat"), new File(saveDir, "data2.dat"), new File(saveDir, "lock.dat")};

			if(this.saveFiles[2].exists() && this.saveFiles[2].length() > 0) {
				FileInputStream fin = new FileInputStream(this.saveFiles[2]);
				this.saveTo = fin.read()^1;
				fin.close();

				if(this.saveFiles[this.saveTo^1].exists()) {
					DataInputStream din = new DataInputStream(new FileInputStream(this.saveFiles[this.saveTo^1]));
					this.saveTag = CompressedStreamTools.readCompressed(din);
					din.close();
				}
				else
					saveTag = new NBTTagCompound();
			}
			else
				saveTag = new NBTTagCompound();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void save(boolean force) {
		if(!this.dirtyStorage.isEmpty() || force) {
			for(AbstractDimStorage inv : this.dirtyStorage) {
				this.saveTag.setTag(inv.owner+"|"+inv.freq+"|"+inv.getType(), inv.saveToTag());
				inv.setClean();
			}

			this.dirtyStorage.clear();

			try {
				File saveFile = this.saveFiles[this.saveTo];
				if(!saveFile.exists())
					saveFile.createNewFile();

				DataOutputStream dout = new DataOutputStream(new FileOutputStream(saveFile));
				CompressedStreamTools.writeCompressed(this.saveTag, dout);
				dout.close();
				FileOutputStream fout = new FileOutputStream(this.saveFiles[2]);
				fout.write(this.saveTo);
				fout.close();
				this.saveTo^=1;
			}
			catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void requestSave(AbstractDimStorage storage) {
		this.dirtyStorage.add(storage);
	}

	// ****************************************************************
	public static void registerPlugin(IDimStoragePlugin plugin) {
		plugins.put(plugin.identifer(), plugin);

		if(serverManager != null)
			serverManager.storageList.put(plugin.identifer(), new ArrayList<AbstractDimStorage>());
		if(clientManager != null)
			clientManager.storageList.put(plugin.identifer(), new ArrayList<AbstractDimStorage>());
	}

	public static void reloadManager(boolean client, World world) {
		DimStorageManager newManager = new DimStorageManager(client, world);

		if(client)
			clientManager = newManager;
		else
			serverManager = newManager;
	}

	public static DimStorageManager instance(boolean client) {
		return client ? clientManager : serverManager;
	}

	// ****************************************************************
	public static class DimStorageSaveHandler {

		@SubscribeEvent
		public void onWorldLoad(Load event) {
			DimStorageManager manager = instance(event.world.isRemote);

			if(event.world.isRemote)
				reloadManager(true, null);
			else if(manager == null)
				reloadManager(false, event.world);
		}

		@SubscribeEvent
		public void onWorldSave(Save event) {
			if(!event.world.isRemote && instance(false) != null)
				instance(false).save(false);
		}

		@SubscribeEvent
		public void onChunkDataLoad(ChunkDataEvent.Load event) {
			if(serverManager == null)
				reloadManager(false, event.world);
		}

		@SubscribeEvent
		public void onWorldUnload(Unload event) {
			if(!event.world.isRemote && !MinecraftServer.getServer().isServerRunning())
				serverManager = null;
		}

	}

	// ****************************************************************
	public boolean isClient() { return this.client; }

}
