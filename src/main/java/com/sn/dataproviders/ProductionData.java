/**
 * This file is for reference only. 
 */

package com.sn.dataproviders;
/**
 * 
 * @author Pankaj Tarar
 *
 */
public class ProductionData {

    private String dbUserName;
    
    private String executionENV;
    
    private String prodOrderQuery;
    
    private String purchaseOrderQuery;
    
    private String quotesQuery;
    
    private String filepath;

    private String validFileName;
    
    private String inValidFileName;
    
    private String filepathRemove;
    /**
     * 
     * @param data String[]
     */
    public ProductionData(final String[] data) {
        this.dbUserName = data[0];
        this.executionENV = data[1];
        this.prodOrderQuery = data[2];
        this.purchaseOrderQuery = data[3];
        this.quotesQuery = data[4];
        this.filepath = data[5];
        this.validFileName = data[6];
        this.inValidFileName = data[7];
        this.filepathRemove = data[8];
    }

    
 /**
  *   
  * @return dbUserName
  */
    public String getdbUserName() {
        return dbUserName;
    }
   /**
    *  
    * @return executionENV
    */
    public String getexecutionENV() {
        return executionENV;
    }
    /**
     * 
     * @return prodOrderQuery
     */
    public String getProdOrderQuery() {
        return prodOrderQuery;
    }
    /**
     * 
     * @return purchaseOrderQuery
     */ 
    public String getPurchaseOrderQuery() {
        return purchaseOrderQuery;
    }
    /**
     * 
     * @return quotesQuery
     */
    public String getQuotesQuery() {
        return quotesQuery;
    }
    /**
     * 
     * @return filepath
     */
    public String getFilePath() {     
        return filepath;
    }
    /**
     * 
     * @return validFileName
     */
    public String getValidFileName() {     
        return validFileName;
    }
    /**
     * 
     * @return inValidFileName
     */
    public String getInValidFileName() {     
        return inValidFileName;
    }
    /**
     * 
     * @return filepathRemove
     */
    public String getFilePathRemove() {       
        return filepathRemove;
    }

}
