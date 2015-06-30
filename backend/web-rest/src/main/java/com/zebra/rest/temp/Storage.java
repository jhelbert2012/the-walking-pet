package com.zebra.rest.temp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.zebra.model.api.CleaningKit;
import com.zebra.model.api.Material;
import com.zebra.model.api.Partner;
import com.zebra.model.api.PrintHead;
import com.zebra.model.api.Printer;
import com.zebra.model.api.Ribbon;

public class Storage {

	private List<Printer>		printers;

	private List<Ribbon>		ribbons;

	private List<Material>		materials;

	private List<CleaningKit>	cleaningKits;

	private List<Partner>		partners;

	private List<PrintHead> printHeads;


	public Storage(List<Printer> printers, List<Ribbon> ribbons, List<Material> materials,
		List<CleaningKit> cleaningKits, List<Partner> partners, List<PrintHead> printHeads) {
		super();
		this.printers = printers;
		this.ribbons = ribbons;
		this.materials = materials;
		this.cleaningKits = cleaningKits;
		this.partners = partners;
		this.printHeads = printHeads;
	}

	public List<Printer> getPrinters() {
		return copy(printers);
	}

	public void setPrinters(List<Printer> printers) {
		this.printers = printers;
	}

	public List<Ribbon> getRibbons() {
		return copy(ribbons);
	}

	public void setRibbons(List<Ribbon> ribbons) {
		this.ribbons = ribbons;
	}

	public List<Material> getMaterials() {
		return copy(materials);
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public List<CleaningKit> getCleaningKits() {
		return copy(cleaningKits);
	}

	public void setCleaningKits(List<CleaningKit> cleaningKit) {
		this.cleaningKits = cleaningKit;
	}

	public List<Partner> getPartners() {
		return copy(partners);
	}

	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}
	
	public List<PrintHead> getPrintHeads() {
		return copy(printHeads);
	}
	
	public void setPrintHeads(List<PrintHead> printHeads) {
		this.printHeads = printHeads;
	}

	/**
	 * Deep copy the list
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private static <T> T copy(T originalList) {
		T copiedList = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(originalList);
			out.close();
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bis);
			copiedList = (T) in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return copiedList;
	}

}
