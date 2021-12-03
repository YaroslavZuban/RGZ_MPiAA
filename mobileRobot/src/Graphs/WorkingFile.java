package Graphs;

import Graphics.Game;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WorkingFile  {
    private final String circle="circle: ";
    private final String Rectangle="Rectangle: ";
    private final String sizeSquare="Size square: ";
    private final String Radius="Radius: ";
    private final String fileName="options.txt";

    public void save(){
        try (FileWriter file=new FileWriter("options.txt",false)) {
            file.write(circle+Game.countIntermediate+"\n");
            file.write(Rectangle+Game.countRectangles+"\n");
            file.write(sizeSquare+Game.splittingX+"\n");
            file.write(Radius+Game.radius+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reading(){
        try (FileReader file=new FileReader(fileName); Scanner scanner=new Scanner(file)){
            int i=0;
            while (scanner.hasNextLine()){
                String line=scanner.nextLine();

                if(i==0){
                    Game.countIntermediate=Integer.parseInt(line.replaceAll("[^0-9]", ""));
                }else if(i==1){
                    Game.countRectangles=Integer.parseInt(line.replaceAll("[^0-9]", ""));
                }else if(i==2){
                    Game.splittingX=Integer.parseInt(line.replaceAll("[^0-9]", ""));
                    Game.splittingY=Integer.parseInt(line.replaceAll("[^0-9]", ""));
                }else if(i==3){
                    Game.radius=Integer.parseInt(line.replaceAll("[^0-9]", ""));
                }

                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
