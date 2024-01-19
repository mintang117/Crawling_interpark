package project1;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class api1 {

    public static void main(String[] args) {
        String url = "https://ticket.interpark.com/TPGoodsList.asp?Ca=Eve&SubCa=Eve_O&Sort=5";

        try {
            // 해당 URL에서 HTML 문서 가져오기
            Document document = Jsoup.connect(url).get();

            // "fw_bold" 클래스와 "Rkdate" 클래스를 가진 항목 추출
            Elements fwBoldElements = document.select(".fw_bold, .Rkdate");

            // 중복을 허용하지 않는 Set을 사용하여 출력
            for (Element element : fwBoldElements) {
                String text = element.text();
                String label = getLabel(element.className(), text);

                System.out.println(label + ": " + text);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 클래스 이름과 텍스트 내용을 기반으로 레이블을 가져오는 사용자 정의 메서드
    private static String getLabel(String className, String text) {
        switch (className) {
            case "fw_bold":
                return "전시";
            case "Rkdate":
                return isDateRange(text) ? "기간" : "장소";
            // 다른 클래스 이름에 대한 필요에 따라 추가하세요.
            default:
                return className;
        }
    }

    // 문자열이 날짜 범위 형식인지 확인하는 메서드
    private static boolean isDateRange(String str) {
        // 날짜 형식에 맞는지 여부를 확인하는 로직을 추가해주세요
        // 예: "2023.11.09~2023.12.19" 형식에 맞는지 확인
        String[] parts = str.split("~");
        if (parts.length == 2) {
            return isDate(parts[0].trim()) && isDate(parts[1].trim());
        }
        return false;
    }

    // 문자열이 날짜 형식인지 확인하는 메서드
    private static boolean isDate(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
