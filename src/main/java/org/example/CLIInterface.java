package org.example;

import java.io.IOException;
import java.util.Scanner;

public class CLIInterface {
    public void takeUserInput() throws IOException, InterruptedException {
        System.out.print("github-activity");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine().trim();
        UserActivityHandle user = new UserActivityHandle(username);
        user.URLConnect(username);

    }
}
