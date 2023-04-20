import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger countOf3 = new AtomicInteger();
    public static AtomicInteger countOf4 = new AtomicInteger();
    public static AtomicInteger countOf5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread firstCondition = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (new StringBuilder(texts[i]).reverse().toString().equals(texts[i])) {
                    sort(texts[i]);
                }
            }
        });
        firstCondition.start();

        //Thread secondCondition = new Thread(() -> {
        //    for (int i = 0; i < texts.length; i++) {
        //        boolean isEquals = false;
        //        for (int j = 0; j < texts[i].length() - 1; j++) {
        //            if (texts[i].charAt(j) == texts[i].charAt(j + 1)) {
        //                isEquals = true;
        //            } else {
        //                isEquals = false;
        //                break;
        //            }
        //        }
        //        if (isEquals) {
        //            sort(texts[i]);
        //        }
        //    }
        //});
        //secondCondition.start();

        Thread thirdCondition = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                boolean increases = false;
                for (int j = 0; j < texts[i].length() - 1; j++) {
                    if (texts[i].charAt(j) <= texts[i].charAt(j + 1)) {
                        increases = true;
                    } else {
                        increases = false;
                        break;
                    }
                }
                if (increases && texts[i].charAt(0) != texts[i].charAt(texts[i].length() - 1)) {
                    sort(texts[i]);
                }
            }
        });
        thirdCondition.start();

        firstCondition.join();
        //secondCondition.join();
        thirdCondition.join();

        System.out.println("Красивых слов с длиной 3: " + countOf3 + " шт.");
        System.out.println("Красивых слов с длиной 4: " + countOf4 + " шт.");
        System.out.println("Красивых слов с длиной 5: " + countOf5 + " шт.");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void sort(String str) {
        switch (str.length()) {
            case 3:
                countOf3.getAndIncrement();
                break;
            case 4:
                countOf4.getAndIncrement();
                break;
            case 5:
                countOf5.getAndIncrement();
        }
    }
}
