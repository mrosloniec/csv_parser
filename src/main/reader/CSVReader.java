package main.reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import main.model.Data;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

	public List<Data> read(String fileName) throws IOException {
		List<Data> dataList = new ArrayList<Data>();
		Reader in = new FileReader(fileName);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
		for (CSVRecord record : records) {
			Data data = new Data();
			String id = record.get("ID");
			data.setId(validateInteger(id));
			data.setArticleid(record.get("ARTICLEID"));
			data.setAttribute(record.get("ATTRIBUTE"));
			data.setValue(record.get("VALUE"));
			String language = record.get("LANGUAGE");
			data.setLanguage(validateShort(language));
			String type = record.get("TYPE");
			data.setType(validateShort(type));
			dataList.add(data);
		}
		return dataList;
	}

	private String validateShort(String value) {
		try {
			Short.parseShort(value);
		} catch (NumberFormatException nfe) {
			return "";
		}
		return value;
	}

	private String validateInteger(String value)  {
		try {
			Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return "";
		}
		return value;
	}

}
