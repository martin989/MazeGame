
public class File {
	private Workbook createXLSFile(String fileName) throws FileNotFoundException, IOException {
		String filePath = System.getProperty("user.dir")+"\\src";
		File file =    new File(filePath+"\\"+fileName);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook workbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if(fileExtensionName.equals(".xlsx")){
			workbook = new XSSFWorkbook(inputStream);
		}else if(fileExtensionName.equals(".xls")){
			workbook = new HSSFWorkbook(inputStream);
		}
		return workbook;
	}
}
