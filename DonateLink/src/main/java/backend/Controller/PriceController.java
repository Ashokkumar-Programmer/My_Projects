package backend.Controller;

import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PriceController {
	public int getproductprice(String productname, String budget) {
    	String product = productname;
    	int price = 0;
    	try {
            Document doc = Jsoup.connect("https://www.amazon.in/s?k="+product+"&ref=nb_sb_noss").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36").get();

            Elements links = doc.select(".a-price-whole");

            int count = links.size();
            int[] p = new int[count];
            int index = 0;

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
            if(budget.equals("high")) {
            	price = prices[prices.length-1];
            }
            else{
            	price = prices[0];
            }
            	
        } catch (IOException e) {
            System.out.println("Error fetching the webpage: " + e.getMessage());
        }
    	return price;
	}
}