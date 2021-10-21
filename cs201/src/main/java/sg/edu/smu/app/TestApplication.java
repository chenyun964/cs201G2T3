package sg.edu.smu.app;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.JSONArray;

public class TestApplication {
    public static void main(String[] args) {
        System.out.println("Hello world");
        File file = new File("data.json");

        try {
            String content = FileUtils.readFileToString(file, "utf-8");
            JSONObject tomJsonObject = new JSONObject(content);
            JSONArray users = tomJsonObject.getJSONArray("users");

            for (int i = 0; i < users.length(); i++) {
                String user = (String) users.get(i);
                System.out.println(user);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
