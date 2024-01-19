package project1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.jsoup.select.Elements;
import java.util.List;
import java.util.ArrayList;
import org.jsoup.select.Elements;
import java.util.concurrent.TimeUnit;

//naver1 클래스의 수정된 부분
public class naver1 {
 public static void main(String[] args) {
     // naver 클래스의 인스턴스 생성
     naver naverInstance = new naver();

     // naver 클래스에서 newUrls 값을 가져오기
     List<String> allNewUrls = naverInstance.getAllNewUrls();

     // 크롤링을 위한 WebDriver 설정
     System.setProperty("webdriver.chrome.driver", "C:\\Users\\minta\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
     WebDriver driver = new ChromeDriver();
     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

     try {
         // 각 NewUrl로 이동하면서 정보 크롤링
         for (String newUrlValue : allNewUrls) {
             // newUrl로 이동
             driver.get(newUrlValue);
             
             
             
             Elements bh9OHElements = Jsoup.parse(driver.getPageSource()).select(".bh9OH");
             printElements(bh9OHElements);
             
             // "LDgIH" 클래스의 요소들 크롤링 및 출력
             Elements PkgBlElements = Jsoup.parse(driver.getPageSource()).select(".PkgBl");
             printElements(PkgBlElements);

             // "A_cdD" 클래스의 요소들 크롤링 및 출력
             Elements U7pYfElements = Jsoup.parse(driver.getPageSource()).select(".w9QyJ.vI8SM.DzD3b");
             System.out.println(U7pYfElements.text());

             Elements tXI2cElements = Jsoup.parse(driver.getPageSource()).select(".O8qbU.tXI2c");
             System.out.println(tXI2cElements.text());
             
             Elements nd2nMImageLinks = Jsoup.parse(driver.getPageSource()).select(".place_thumb img");
             printImageLinks(nd2nMImageLinks, 5);
             
             System.out.println("--------------------------------------");
         }

     } catch (Exception e) {
         e.printStackTrace();
     } finally {
         // WebDriver 종료
         driver.quit();
     }
 }

 // Elements를 출력하는 메서드
 private static void printElements(Elements elements) {
     for (Element element : elements) {
         System.out.println(element.text());
     }
 }
 private static void printImageLinks(Elements imageLinks, int maxCount) {
     int count = 0;
     for (Element imageLink : imageLinks) {
         if (count >= maxCount) {
             break;
         }

         String imageUrl = imageLink.attr("src");
         System.out.println(imageUrl);
         count++;
     }
 }
}
