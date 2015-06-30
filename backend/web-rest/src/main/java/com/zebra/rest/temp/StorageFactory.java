package com.zebra.rest.temp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.core.io.Resource;

import com.zebra.constant.model.Adhesive;
import com.zebra.constant.model.Application;
import com.zebra.constant.model.Boolean;
import com.zebra.constant.model.CardTechnology;
import com.zebra.constant.model.Color;
import com.zebra.constant.model.Dmar;
import com.zebra.constant.model.Industry;
import com.zebra.constant.model.MaterialType;
import com.zebra.constant.model.PartnerType;
import com.zebra.constant.model.PrintTechnology;
import com.zebra.constant.model.ProductType;
import com.zebra.constant.model.Region;
import com.zebra.constant.model.RibbonApplication;
import com.zebra.constant.model.RibbonColor;
import com.zebra.constant.model.VerticalMarket;
import com.zebra.model.api.CleaningKit;
import com.zebra.model.api.Material;
import com.zebra.model.api.Partner;
import com.zebra.model.api.Printer;
import com.zebra.model.api.Ribbon;

public class StorageFactory {

	private static final String	NOT_APPLICABLE	= "NA";

	public static synchronized Storage createStorage(Resource sstLocation, Resource isvLocation) throws IOException {

		if (!sstLocation.exists())
			throw new BeanInitializationException("The SST spreadsheet cannot be found in the classpath");

		if (!sstLocation.exists())
			throw new BeanInitializationException("The ISV spreadsheet cannot be found in the classpath");

		XSSFWorkbook sstWorkbook = new XSSFWorkbook(sstLocation.getInputStream());
		List<Printer> printers = extractPrinters(sstWorkbook.getSheet("printer"));
		List<CleaningKit> cleaningKits = extractCleaningKits(sstWorkbook.getSheet("cleaningKits"), printers);
		List<Ribbon> ribbons = extractRibbons(sstWorkbook.getSheet("ribbon"), printers);
		List<Material> materials = extractMaterials(sstWorkbook.getSheet("material"), printers, ribbons);

		XSSFWorkbook isvWorkbook = new XSSFWorkbook(isvLocation.getInputStream());
		List<Partner> partners = extractPartners(isvWorkbook.getSheet("ISVs"));

		return new Storage(printers, ribbons, materials, cleaningKits, partners, null);

	}

	private static List<Printer> extractPrinters(XSSFSheet sheet) {
		List<Printer> printers = new ArrayList<Printer>();
		int rowCounter = 0;
		int colCounter = 0;

		try {
			for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {

				// skip first row
				if (rowCounter == 0) {
					rowIterator.next();
					rowCounter++;
				}
				Row row = rowIterator.next();

				colCounter = 0;
				Printer printer = new Printer();
				for (Iterator<Cell> cellIterator = row.iterator(); cellIterator.hasNext();) {

					Cell cell = cellIterator.next();

					if (colCounter == 0) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						printer.setId(cell.getStringCellValue());
					}
					if (colCounter == 1) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						printer.setName(cell.getStringCellValue());
					}
					if (colCounter == 2) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						printer.setImageLink(cell.getStringCellValue());
					}
					colCounter++;
				}
				printers.add(printer);

				rowCounter++;
			}
		} catch (IllegalStateException e) {
			throw new IllegalStateException("There is an issue in cell " + colCounter + " - " + rowCounter
				+ " of sheet printer", e);
		}
		return printers;
	}

	private static List<CleaningKit> extractCleaningKits(XSSFSheet sheet, List<Printer> printers) {
		List<CleaningKit> accessories = new ArrayList<CleaningKit>();
		int rowCounter = 0;
		int colCounter = 0;

		try {

			for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {

				// skip first row
				if (rowCounter == 0) {
					rowIterator.next();
					rowCounter++;
				}
				Row row = rowIterator.next();

				colCounter = 0;
				CleaningKit cleaningKit = new CleaningKit();

				for (Iterator<Cell> cellIterator = row.iterator(); cellIterator.hasNext();) {

					Cell cell = cellIterator.next();

					if (colCounter == 0) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cleaningKit.setId(cell.getStringCellValue());
					}
					if (colCounter == 1) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cleaningKit.setName(cell.getStringCellValue());
					}
					if (colCounter == 2) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cleaningKit.setDescription(cell.getStringCellValue());
					}
					if (colCounter == 3) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())) {
							List<Printer> ribbonPrinters = new ArrayList<Printer>();
							String[] printerValues = cell.getStringCellValue().split(",");
							for (String value : printerValues) {
								for (Printer printer : printers) {
									if (printer.getId().equalsIgnoreCase(value))
										ribbonPrinters.add(printer);
								}
							}
							cleaningKit.setPrinters(ribbonPrinters);
						}
					}
					if (colCounter == 4) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							cleaningKit.getDmarSkus().put(Dmar.CDW, cell.getStringCellValue());
					}
					if (colCounter == 5) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							cleaningKit.getDmarSkus().put(Dmar.PCC, cell.getStringCellValue());
					}

					colCounter++;
				}
				accessories.add(cleaningKit);

				rowCounter++;
			}
		} catch (IllegalStateException e) {
			throw new IllegalStateException("There is an issue in cell " + colCounter + " - " + rowCounter
				+ " of sheet accessory", e);
		}
		return accessories;
	}

	private static List<Ribbon> extractRibbons(XSSFSheet sheet, List<Printer> printers) {
		List<Ribbon> ribbons = new ArrayList<Ribbon>();
		int rowCounter = 0;
		int colCounter = 0;

		try {

			for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {

				// skip first row
				if (rowCounter == 0) {
					rowIterator.next();
					rowCounter++;
				}
				Row row = rowIterator.next();

				colCounter = 0;
				Ribbon ribbon = new Ribbon();

				for (Iterator<Cell> cellIterator = row.iterator(); cellIterator.hasNext();) {

					Cell cell = cellIterator.next();

					if (colCounter == 0) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						ribbon.setId(cell.getStringCellValue());
					}
					if (colCounter == 1) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						ribbon.setName(cell.getStringCellValue());
					}
					if (colCounter == 2) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						ribbon.setDescription(cell.getStringCellValue());
					}
					if (colCounter == 3) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							ribbon.setRibbonColor(RibbonColor.valueOf(cell.getStringCellValue()));
					}
					if (colCounter == 4) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if (cell.getNumericCellValue() != 0)
							ribbon.setWidth(cell.getNumericCellValue());
					}
					if (colCounter == 5) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if (cell.getNumericCellValue() != 0)
							ribbon.setLength(cell.getNumericCellValue());
					}
					if (colCounter == 6) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							ribbon.setPrintTechnology(PrintTechnology.valueOf(cell.getStringCellValue()));
					}
					if (colCounter == 7) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())) {
							List<Printer> ribbonPrinters = new ArrayList<Printer>();
							String[] printerValues = cell.getStringCellValue().split(",");
							for (String value : printerValues) {
								for (Printer printer : printers) {
									if (printer.getId().equalsIgnoreCase(value))
										ribbonPrinters.add(printer);
								}
							}
							ribbon.setPrinters(ribbonPrinters);
						}
					}
					if (colCounter == 8) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							ribbon.setRibbonApplication(RibbonApplication.valueOf(cell.getStringCellValue()));
					}
					if (colCounter == 9) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if (cell.getNumericCellValue() != 0)
							ribbon.setImagesPerRoll((int) cell.getNumericCellValue());
					}
					if (colCounter == 10) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							ribbon.setDetailedSpecs(cell.getStringCellValue());
					}
					if (colCounter == 11) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							ribbon.getDmarSkus().put(Dmar.CDW, cell.getStringCellValue());
					}
					if (colCounter == 12) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							ribbon.getDmarSkus().put(Dmar.PCC, cell.getStringCellValue());
					}

					colCounter++;
				}
				ribbons.add(ribbon);

				rowCounter++;
			}
		} catch (IllegalStateException e) {
			throw new IllegalStateException("There is an issue in cell " + colCounter + " - " + rowCounter
				+ " of sheet ribbon", e);
		}
		return ribbons;
	}

	private static List<Material> extractMaterials(XSSFSheet sheet, List<Printer> printers, List<Ribbon> ribbons) {
		List<Material> supplies = new ArrayList<Material>();
		int rowCounter = 0;
		int colCounter = 0;

		try {

			for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {

				// skip first row
				if (rowCounter == 0) {
					rowIterator.next();
					rowCounter++;
				}
				Row row = rowIterator.next();

				colCounter = 0;
				Material supply = new Material();

				for (Iterator<Cell> cellIterator = row.iterator(); cellIterator.hasNext();) {

					Cell cell = cellIterator.next();

					if (colCounter == 0) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						supply.setId(cell.getStringCellValue());
					}
					if (colCounter == 1) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						supply.setName(cell.getStringCellValue());
					}
					if (colCounter == 2) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						supply.setDescription(cell.getStringCellValue());
					}
					if (colCounter == 3) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							supply.setAdditionalInfo(cell.getStringCellValue());
					}
					if (colCounter == 4) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if (cell.getNumericCellValue() != 0)
							supply.setWidth(cell.getNumericCellValue());
					}
					if (colCounter == 5) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if (cell.getNumericCellValue() != 0)
							supply.setLength(cell.getNumericCellValue());
					}
					if (colCounter == 6) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						List<Printer> supplyPrinters = new ArrayList<Printer>();

						String[] printerValues = cell.getStringCellValue().split(",");
						for (String value : printerValues) {
							for (Printer printer : printers) {
								if (printer.getId().equalsIgnoreCase(value))
									supplyPrinters.add(printer);
							}
						}
						supply.setPrinters(supplyPrinters);
					}
					if (colCounter == 7) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue())) {
							List<Ribbon> supplyRibbons = new ArrayList<Ribbon>();
							String[] ribbonValues = cell.getStringCellValue().split(",");
							for (String value : ribbonValues) {
								for (Ribbon ribbon : ribbons) {
									if (ribbon.getId().equalsIgnoreCase(value))
										supplyRibbons.add(ribbon);
								}
							}
							supply.setStandardRibbons(supplyRibbons);
						}
					}
					if (colCounter == 8) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue())) {
							List<Ribbon> supplyRibbons = new ArrayList<Ribbon>();
							String[] ribbonValues = cell.getStringCellValue().split(",");
							for (String value : ribbonValues) {
								for (Ribbon ribbon : ribbons) {
									if (ribbon.getId().equalsIgnoreCase(value))
										supplyRibbons.add(ribbon);
								}
							}
							supply.setHighRibbons(supplyRibbons);
						}
					}
					if (colCounter == 9) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							supply.setPrintTechnology(PrintTechnology.valueOf(cell.getStringCellValue()));
					}
					if (colCounter == 10) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
                                                String cellValue = cell.getStringCellValue();
                                                if(StringUtils.isNotBlank(cellValue)){
                                                    supply.setProductType(ProductType.valueOf(cellValue));
                                                }
					}
					if (colCounter == 11) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							supply.setMaterialType(MaterialType.valueOf(cell.getStringCellValue()));
					}
					if (colCounter == 12) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							supply.setAdhesive(Adhesive.valueOf(cell.getStringCellValue()));
					}
					if (colCounter == 13) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							supply.setPerforated(com.zebra.constant.model.Boolean.valueOf(cell.getStringCellValue()));
					}
					if (colCounter == 14) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							supply.setColor(Color.valueOf(cell.getStringCellValue()));
					}
					if (colCounter == 15) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							supply.setIndustry(Industry.valueOf(cell.getStringCellValue()));
					}
					if (colCounter == 16) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if (cell.getNumericCellValue() != 0)
							supply.setAmountPerRoll((int) cell.getNumericCellValue());
					}
					if (colCounter == 17) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if (cell.getNumericCellValue() != 0)
							supply.setAmountInFeetPerRoll((int) cell.getNumericCellValue());
					}
					if (colCounter == 18) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if (cell.getNumericCellValue() != 0)
							supply.setQuantityPerCarton((int) cell.getNumericCellValue());
					}
					if (colCounter == 19) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if (cell.getNumericCellValue() != 0)
							supply.setCardsPerBox((int) cell.getNumericCellValue());
					}
					if (colCounter == 20) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							supply.setCardTechnology(CardTechnology.valueOf(cell.getStringCellValue()));
					}
					if (colCounter == 21) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if (cell.getNumericCellValue() != 0)
							supply.setCardThickness((int) cell.getNumericCellValue());
					}
					if (colCounter == 22) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							supply.setDetailedSpecs(cell.getStringCellValue());
					}
					if (colCounter == 23) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							supply.setPreferred(com.zebra.constant.model.Boolean.valueOf(cell.getStringCellValue()));
					}
					if (colCounter == 24) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							supply.getDmarSkus().put(Dmar.CDW, cell.getStringCellValue());
					}
					if (colCounter == 25) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							supply.getDmarSkus().put(Dmar.PCC, cell.getStringCellValue());
					}

					colCounter++;
				}
				supplies.add(supply);

				rowCounter++;
			}
		} catch (IllegalStateException e) {
			throw new IllegalStateException("There is an issue in cell " + colCounter + " - " + rowCounter
				+ " of sheet material", e);
		}
		return supplies;
	}

	private static List<Partner> extractPartners(XSSFSheet sheet) {
		List<Partner> partners = new ArrayList<Partner>();
		int rowCounter = 0;
		int colCounter = 0;

		try {
			for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {

				rowCounter++;
				Row row = rowIterator.next();

				// skip first 2 rows
				if (rowCounter == 1 || rowCounter == 2) {
					continue;
				}

				colCounter = 0;
				Partner partner = new Partner();
				for (Iterator<Cell> cellIterator = row.iterator(); cellIterator.hasNext();) {

					Cell cell = cellIterator.next();

					if (colCounter == 0) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							partner.setId(cell.getStringCellValue());
					}
					if (colCounter == 1) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							partner.setName(cell.getStringCellValue());
					}
					if (colCounter == 2) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getRegions().add(Region.APAC);
					}
					if (colCounter == 3) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getRegions().add(Region.EMEA);
					}
					if (colCounter == 4) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getRegions().add(Region.LATAM);
					}
					if (colCounter == 5) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getRegions().add(Region.NA);
					}
					if (colCounter == 6) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getVerticalMarkets().add(VerticalMarket.R);
					}
					if (colCounter == 7) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getVerticalMarkets().add(VerticalMarket.M);
					}
					if (colCounter == 8) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getVerticalMarkets().add(VerticalMarket.TL);
					}
					if (colCounter == 9) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getVerticalMarkets().add(VerticalMarket.HOSP);
					}
					if (colCounter == 10) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getVerticalMarkets().add(VerticalMarket.HC);
					}
					if (colCounter == 11) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getVerticalMarkets().add(VerticalMarket.FB);
					}
					if (colCounter == 12) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getVerticalMarkets().add(VerticalMarket.S);
					}
					if (colCounter == 13) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getVerticalMarkets().add(VerticalMarket.G);
					}
					if (colCounter == 14) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.AM);
					}
					if (colCounter == 15) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.CA);
					}
					if (colCounter == 16) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.DM);
					}
					if (colCounter == 17) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.DSD);
					}
					if (colCounter == 18) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.EC);
					}
					if (colCounter == 19) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.FS);
					}
					if (colCounter == 20) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.HS);
					}
					if (colCounter == 21) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.K);
					}
					if (colCounter == 22) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.L);
					}
					if (colCounter == 23) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.MPOS);
					}
					if (colCounter == 24) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.MPRINT);
					}
					if (colCounter == 25) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.MWF);
					}
					if (colCounter == 26) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.PM);
					}
					if (colCounter == 27) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.M);
					}
					if (colCounter == 28) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.POS);
					}
					if (colCounter == 29) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.RFID);
					}
					if (colCounter == 30) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.SC);
					}
					if (colCounter == 31) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.T);
					}
					if (colCounter == 32) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.WM);
					}
					if (colCounter == 33) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.LC);
					}
					if (colCounter == 34) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue()))
							partner.getApplications().add(Application.SII);
					}
					if (colCounter == 35) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())) {
							partner.setValidated(Boolean.TRUE);
						} else {
							partner.setValidated(Boolean.FALSE);
						}
					}
					if (colCounter == 36) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							partner.setUrl(cell.getStringCellValue());
					}
					if (colCounter == 37) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							partner.setLogo(cell.getStringCellValue());
					}
					if (colCounter == 38) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (StringUtils.isNotBlank(cell.getStringCellValue())
							&& !NOT_APPLICABLE.equalsIgnoreCase(cell.getStringCellValue()))
							partner.setDescription(cell.getStringCellValue());
					}

					colCounter++;
				}

				partner.setType(PartnerType.ISV);
				partners.add(partner);

				rowCounter++;
			}
		} catch (IllegalStateException e) {
			throw new IllegalStateException("There is an issue in cell " + colCounter + " - " + rowCounter
				+ " of sheet partner", e);
		}
		return partners;
	}
}
