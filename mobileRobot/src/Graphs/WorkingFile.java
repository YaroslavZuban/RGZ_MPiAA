package Graphs;

import Graphics.Game;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkingFile  {
    public void saveCircle(ArrayList<Circle> circles){
        try (FileWriter file=new FileWriter("circle.txt",false)) {
            for (int i=0;i<circles.size();i++){
                file.write("x: "+circles.get(i).point.getX()+" | " +
                        "y: "+circles.get(i).point.getY()+" | " +
                        "radius: "+circles.get(i).radius+" | " +
                        "type: "+circles.get(i).getType()+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRectangle(ArrayList<Rectangle> rectangle){
        try (FileWriter file=new FileWriter("rectangle.txt",false)) {
            for (int i=0;i<rectangle.size();i++){
                file.write("x: "+rectangle.get(i).point.getX()+" | " +
                        "y: "+rectangle.get(i).point.getY()+" | " +
                        "w: "+rectangle.get(i).w+" | " +
                        "h: "+rectangle.get(i).h+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSizeSquare(int size){
        try (FileWriter file=new FileWriter("size.txt",false)) {
           file.write("size: "+size);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readingCircle(ArrayList<Circle> circles){
        try (FileReader file=new FileReader("circle.txt"); Scanner scanner=new Scanner(file)){
            circles.clear();

            while (scanner.hasNextLine()){
                String line=scanner.nextLine();
                Pattern p = Pattern.compile("(\\d+\\.?\\d*)");

                ArrayList<Integer> coordinates=new ArrayList<>();
                Matcher m = p.matcher(line);

                while (m.find()){
                    int temp= (int) Double.parseDouble(m.group());
                    coordinates.add(temp);
                }

                Circle circle=new Circle(coordinates.get(0),
                        coordinates.get(1),
                        coordinates.get(2),
                        coordinates.get(3));
                circles.add(circle);
            }

            Collections.reverse(circles);
            Game.countIntermediate=circles.size()-2;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readingRectangle(ArrayList<Rectangle> rectangle){
        try (FileReader file=new FileReader("rectangle.txt"); Scanner scanner=new Scanner(file)){
            rectangle.clear();

            while (scanner.hasNextLine()){
                String line=scanner.nextLine();
                Pattern p = Pattern.compile("(\\d+\\.?\\d*)");

                ArrayList<Integer> coordinates=new ArrayList<>();
                Matcher m = p.matcher(line);

                while (m.find()){
                    int temp= (int) Double.parseDouble(m.group());
                    coordinates.add(temp);
                }

                Rectangle rectangle1=new Rectangle(coordinates.get(0),
                        coordinates.get(1),
                        coordinates.get(3),
                        coordinates.get(2));

                rectangle.add(rectangle1);
            }

            Collections.reverse(rectangle);
            Game.countRectangles=rectangle.size();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readingSizeSquare(){
        try (FileReader file=new FileReader("size.txt"); Scanner scanner=new Scanner(file)){
            while (scanner.hasNextLine()){
                String line=scanner.nextLine();
                Pattern p = Pattern.compile("(\\d+\\.?\\d*)");

                Matcher m = p.matcher(line);

                while (m.find()){
                   Game.splittingX= (int) Double.parseDouble(m.group());
                   Game.splittingY= (int) Double.parseDouble(m.group());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
