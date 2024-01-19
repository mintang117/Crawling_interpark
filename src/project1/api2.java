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

public class api2 {
    public static void main(String[] args) {
        // Chrome 브라우저를 실행 (시스템에 설치된 ChromeDriver 사용)
        WebDriver driver = new ChromeDriver();

        try {
            // 크롤링하려는 페이지로 이동
            driver.get("https://ticket.interpark.com/TPGoodsList.asp?Ca=Mus");

            // "fw_bold" 클래스를 가진 링크들을 찾아서 반복문을 통해 각 링크에 접속
            List<WebElement> links = driver.findElements(By.className("fw_bold"));
            for (WebElement link : links) {
                // 링크에 접속
                link.click();

                // 현재 페이지의 HTML을 JSoup을 사용하여 파싱
                Document document = Jsoup.parse(driver.getPageSource());

                // "class=contentDetail"를 가진 요소를 찾아서 출력
                Element contentDetail = document.selectFirst(".content");
                Element prdTitleDetail = document.selectFirst(".prdTitle");
                Element popPlaceInfoDetail = document.selectFirst(".popPlaceInfo");
                Element infoBtnDetail = document.selectFirst(".infoBtn");
                Element infoTextDetail = document.selectFirst(".infoText");
                Element infoPriceListDetail = document.selectFirst(".infoPriceList");

                if (contentDetail != null) {
                    System.out.println(prdTitleDetail.text() + " : " + popPlaceInfoDetail.text() + " , " + infoBtnDetail.text() + " , " + infoTextDetail.text() + " , " + infoPriceListDetail.text());

                    // 이미지 태그를 선택하여 이미지 URL 출력
                    Elements imgElements1 = document.select(".posterBoxTop img");
                    Elements imgElements2 = document.select(".prdContents.detail img");

                    // 출력
                    for (Element imgElement : imgElements1) {
                        String imgSrc = imgElement.attr("src");
                        System.out.println("대표이미지: " + imgSrc);
                    }

                    for (Element imgElement : imgElements2) {
                        String altText = imgElement.attr("alt");
                        // "프로필 사진"이 아닌 경우에만 출력
                        if (!"프로필 사진".equals(altText)) {
                            String imgSrc = imgElement.attr("src");
                            System.out.println("관련이미지: " + imgSrc);
                        }
                    }
                }

                // 브라우저 뒤로 가기
                driver.navigate().back();
            }
        } finally {
            // 브라우저 종료
            driver.quit();
        }
    }
}
