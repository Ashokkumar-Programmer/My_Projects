package backend.Controller;

import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PriceController {
	public int getproductprice(String productname, String category) {
    	int price = 0;
    	String url = null, classname = null;
    	Document doc;
    	Elements links = null;
    	if(category.equals("food")) {
    		url = "https://dir.indiamart.com/search.mp?ss="+productname+"+1kg&prdsrc=1&v=4&mcatid=183956&catid=171&tags=res";
    		classname = ".price";
    		try {
				doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36").get();
				links = doc.select(classname);
				int count = links.size();
				int[] p = new int[count];
				int index = 0;
				if(links.size()==0) {
					return 0;
				}
				for (Element link : links) {
				    try {
				    	String arr[] = link.text().split(" ");
				    	String s = arr[1].replace("₹", "");
				    	//System.out.println(s.split("/")[0]);
				        p[index++] = Integer.parseInt(s.split("/")[0].replace(",", ""));
				    } catch (NumberFormatException e) {
				        link.text();
				    }
				}
				Arrays.sort(p);
				int[] prices = Arrays.stream(p).filter(x -> x != 0).toArray();
				return prices[0];
			} catch (IOException e) {
				e.printStackTrace();
			} 
    	}
    	else if(category.equals("clothes")) {
    		url = "https://www.amazon.in/s?k="+productname+"&ref=nb_sb_noss";
    		classname = ".a-price-whole";
    		try {
				doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36").get();
				links = doc.select(classname);
				int count = links.size();
				int[] p = new int[count];
				int index = 0;
				if(links.size()==0) {
					return 0;
				}
				for (Element link : links) {
				    try {
				    	String s = link.text().replace(",", "");
				        p[index++] = Integer.parseInt(s);
				    } catch (NumberFormatException e) {
				        link.text();
				    }
				}
				Arrays.sort(p);
				int[] prices = Arrays.stream(p).filter(x -> x != 0).toArray();
				return prices[0];
			} catch (IOException e) {
				e.printStackTrace();
			} 
    	}
    	else if(category.equals("furniture")) {
    		url = "https://www.amazon.in/s?k="+productname+"&ref=nb_sb_noss";
    		classname = ".a-price-whole";
    		try {
				doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36").get();
				links = doc.select(classname);
				int count = links.size();
				int[] p = new int[count];
				int index = 0;
				if(links.size()==0) {
					return 0;
				}
				for (Element link : links) {
				    try {
				    	String s = link.text().replace(",", "");
				        p[index++] = Integer.parseInt(s);
				    } catch (NumberFormatException e) {
				        link.text();
				    }
				}
				Arrays.sort(p);
				int[] prices = Arrays.stream(p).filter(x -> x != 0).toArray();
				return prices[0];
			} catch (IOException e) {
				e.printStackTrace();
			} 
    	}
    	return 0;
    	
	}
}