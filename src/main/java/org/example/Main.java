package org.example;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Usage: java GitHubActivityCLI <username>");
            return;
        }
        String username = args[0];
        GitHubApiClient apiClient = new GitHubApiClient();
        EventMapFromApi mapper = new EventMapFromApi();
        GroupingData grouper = new GroupingData();
        Controller controller = new Controller(username, apiClient, mapper, grouper);
        controller.process();
        controller.print();

    }
}