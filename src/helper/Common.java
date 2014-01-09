/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author pratikmac
 */
public class Common {

    private static String GetFileExtension(String fname2) {
        String fileName = fname2;
        String fname = "";
        String ext = "";
        int mid = fileName.lastIndexOf(".");
        fname = fileName.substring(0, mid);
        ext = fileName.substring(mid + 1, fileName.length());
        return ext;
    }

    public HashMap getImportExcelData(String filename) throws IOException {
        HashMap Hm = new HashMap();
        String fileExtn = GetFileExtension(filename);
        InputStream file = new FileInputStream(filename);
        Workbook wb_xssf;
        Workbook wb_hssf;
        Sheet sheet = null;
        if (fileExtn.equalsIgnoreCase("xlsx")) {
            wb_xssf = new XSSFWorkbook(file);
            sheet = wb_xssf.getSheetAt(0);
        }
        if (fileExtn.equalsIgnoreCase("xls")) {
            POIFSFileSystem fs = new POIFSFileSystem(file);
            wb_hssf = new HSSFWorkbook(fs);
            sheet = wb_hssf.getSheetAt(0);
        }
        System.out.println("getImportExcelData()");

        int tempCount = 0;
        try {
            Iterator<Row> tempIteraotr22 = sheet.rowIterator();
            boolean checkHearderName = true;
            while (tempIteraotr22.hasNext()) {
                Row row22 = tempIteraotr22.next();

                if (row22.getRowNum() == 0) {
                    if (!row22.getCell(0).toString().equals("Sno")) {
                        System.out.println("Sno");
                        checkHearderName = false;
                    } else if (!row22.getCell(1).toString().equals("ID")) {
                        System.out.println("ID");
                        checkHearderName = false;
                    } else if (!row22.getCell(2).toString().equals("Name")) {
                        System.out.println("emply Name");
                        checkHearderName = false;
                    } else if (!row22.getCell(3).toString().equals("Field manager")) {
                        System.out.println("Field manager");
                        checkHearderName = false;
                    } else if (!row22.getCell(4).toString().equals("Salutation")) {
                        System.out.println("Salutation");
                        checkHearderName = false;
                    }else if (!row22.getCell(5).toString().equals("EMail")) {
                        System.out.println("EMail");
                        checkHearderName = false;
                    } else if (!row22.getCell(6).toString().trim().equals("MobileNo")) {
                         System.out.println("MobileNo");
                         checkHearderName = false;
                    } else if (row22.getCell(7) != null) {
                        checkHearderName = false;
                        System.out.println("extra row" + row22.getCell(7));
                    }
                } else {
                    break;
                }
            }
            if (checkHearderName == true) {
                Iterator<Row> tempIteraotr = sheet.rowIterator();
                List<BanyanAppBean> lst = new ArrayList<BanyanAppBean>();
                List data = new ArrayList();
                while (tempIteraotr.hasNext()) {
                    Row row = tempIteraotr.next();
                    tempCount++;
                    boolean isRowValid = true;
                    if ((row.getCell(0) == null || row.getCell(0).toString().trim().length() == 0)
                            && (row.getCell(1) == null || row.getCell(1).toString().trim().length() == 0)
                            && (row.getCell(2) == null || row.getCell(2).toString().trim().length() == 0)
                            && (row.getCell(3) == null || row.getCell(3).toString().trim().length() == 0)
                            && (row.getCell(4) == null || row.getCell(4).toString().trim().length() == 0)
                            && (row.getCell(5) == null || row.getCell(5).toString().trim().length() == 0)
                            && (row.getCell(6) == null || row.getCell(6).toString().trim().length() == 0)) {
                        isRowValid = false;
                        tempCount--;
                    }
                    if (row.getRowNum() == 0 || !isRowValid) {
                        continue;
                    }

                    BanyanAppBean tempBean = new BanyanAppBean();

                    tempBean.setSlNo(row.getCell(0).toString().trim());
                    tempBean.setId(row.getCell(1).toString().trim());
                    tempBean.setName(row.getCell(2).toString().trim());
                    tempBean.setFieldManager(row.getCell(3).toString().trim());
                    tempBean.setSalutation(row.getCell(4).toString().trim());
                    tempBean.setEmail(row.getCell(5).toString().trim());
                    tempBean.setMobNo((long)row.getCell(6).getNumericCellValue());
                    
                    tempBean.setChk(Boolean.TRUE);
                    data.add(row.getCell(1).toString().trim());
                    data.add(row.getCell(2).toString().trim());
                    data.add(row.getCell(5).toString().trim());
                    data.add(tempBean.getChk());
                    lst.add(tempBean);

                }
                Hm.put("list", lst);
                Hm.put("temcount", tempCount - 1);
                Hm.put("data", data);
            }

        } catch (Exception ee) {
            System.out.println("Exception in getImportExcelData() in while-" + ee);
            ee.printStackTrace();
        } finally {
            file.close();
        }

        return Hm;
    }
}