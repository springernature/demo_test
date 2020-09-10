package com.sn.dataproviders;

/**
 * This file is for reference only. 
 */

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.sn.apt.excel.read.ReadExcelData;
/**
 * 
 * @author Pankaj Tarar
 *
 */
public class POFlow {

    private static final Logger LOGGER = LogManager.getLogger(POFlow.class);

    private String currentDirectory = System.getProperty("user.dir");

    private String filename = currentDirectory + "\\TestData";
    private static ReadExcelData readExcelData = new ReadExcelData();

    /***
     * @author ptn9587
     * @return data
     * @throws IOException
     */
    @DataProvider(name = "ProductionData")
    public Object[][] getProductionData() throws IOException {
        Object[][] data = new Object[1][9];
        try {
            data = readExcelData.readExcel(filename, "ProductionData.xlsx", "Production");

        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return data;
    }

}
