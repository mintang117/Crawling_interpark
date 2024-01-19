package project1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

public class naver {
    // newUrl 변수 추가
    private String newUrl;
    private List<String> allNewUrls = new ArrayList<>();
    
    // 생성자
    public naver() {
        // 크롬 드라이버 경로 설정
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\minta\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        String url = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%A0%84%EC%8B%9C";

        try {
            WebDriver driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            driver.get(url);

            // 중복 링크를 방지하기 위한 Set
            Set<String> visitedLinks = new HashSet<>();
            
            
            while (true) {
                Document doc = Jsoup.parse(driver.getPageSource());
                Elements dataBoxes = doc.select("div.card_item");

                for (Element dataBox : dataBoxes) {
                    Element titleElement = dataBox.selectFirst(".title");
                    Element infoElement = dataBox.selectFirst(".info");
                    WebElement dataBoxElement = driver.findElement(By.xpath(".//*"));
                    WebElement buttonArea = dataBoxElement.findElement(By.className("btn_place"));

                    String exhibition = (titleElement != null) ? titleElement.text() : "N/A";
                    String infoText = (infoElement != null) ? infoElement.text() : "N/A";
                    String buttonAreaLink = buttonArea.getAttribute("href");
                    String lastNumbers = extractLastNumbers(buttonAreaLink);
                    
                    
                    
                    // 중복된 링크 체크
                    if (!visitedLinks.contains(buttonAreaLink)) {
                        visitedLinks.add(buttonAreaLink);  // 방문한 링크로 추가
                        allNewUrls.add("https://pcmap.place.naver.com/place/" + lastNumbers + "/home");
                    }

                    visitedLinks.add(buttonAreaLink);  // 방문한 링크로 추가

                    System.out.println(exhibition);
                    System.out.println(infoText);

                    // 정규표현식을 사용하여 맨 마지막 숫자 추출
                 
                    // newUrl 값 설정
                    newUrl = "https://pcmap.place.naver.com/place/" + lastNumbers + "/home";

                    String[] infoParts = infoText.split("\\. ");
                    String period = "N/A";
                    String place = "N/A";

                    if (infoParts.length == 2) {
                        period = infoParts[0].trim();
                        place = infoParts[1].trim();
                    }

                    System.out.println(period);
                    System.out.println(place);
                    System.out.println("--------------");
                }

                WebElement nextButton = driver.findElement(By.cssSelector("a.pg_next"));
                if (nextButton.isEnabled()) {
                    nextButton.click();
                    Thread.sleep(2000);

                } else {
                    // npgs_now와 npgs_total이 같을 때 종료
                    String npgsNow = doc.selectFirst("div#npgs_now").text();
                    String npgsTotal = doc.selectFirst("div#npgs_total").text();

                    if (npgsNow.equals(npgsTotal)) {
                        System.out.println("Crawling completed.");
                        break;
                    }
                }

                doc = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<String> getAllNewUrls() {
        return allNewUrls;
    }
    // 정규표현식을 사용하여 맨 마지막 숫자 추출
    private static String extractLastNumbers(String text) {
        Pattern pattern = Pattern.compile("\\d+$");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        } else {
            return "N/A";
        }
    }

    // newUrl 값을 반환하는 메서드 추가
    public String getNewUrl() {
        return newUrl;
    }
}
