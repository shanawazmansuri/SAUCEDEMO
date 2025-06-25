/*package com.utilities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PDFReader {
	public static String pdfContent;
	
	@Test
	public static void readPDF(String pdfPath) throws IOException {
		URL url = new URL(pdfPath);
		InputStream is = url.openStream();
		BufferedInputStream fileparse = new BufferedInputStream(is);
		PDDocument doc = null;

		doc = PDDocument.load(fileparse);
		pdfContent = new PDFTextStripper().getText(doc);
		System.out.println(pdfContent);
	}
}
*/
