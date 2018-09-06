package Pacman.Files;

import Pacman.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecordsFile {
    private File file;
    List<Data>  data;

    public RecordsFile() {
        file = new File("Records.txt");
        data = new ArrayList<Data>();
        if (!file.exists()) {
            try {
                file.createNewFile();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Scanner scanner = new Scanner(new FileReader(file));
            while (scanner.hasNextLine())
                data.add(new Data(scanner.next(), Integer.parseInt(scanner.next())));
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void write(Data newData) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getNickname().equals(newData.getNickname())) {
                if (data.get(i).getScore() < newData.getScore()) {
                    data.remove(i);
                    break;
                }
                else
                    return;
            }
        }

            int place;
            for (place = 0; place < data.size() && data.get(place).getScore() > newData.getScore(); place++) ;
            data.add(place, newData);

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            for (int i = 0; i < data.size(); i++) {
                bufferedWriter.write(data.get(i).getNickname() + " " + data.get(i).getScore());
                if (i < data.size() - 1)
                    bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Data> getData() {
        return data;
    }
}
