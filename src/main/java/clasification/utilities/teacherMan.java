/*
 * Copyright (C) 2017 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package clasification.utilities;
//This Package incorporates Objects that help in the service construction as Tools or structural elements.
//In this package we define Objects and methods where we use for file handling and reading, Classification service 
//definition, server conection and Classification Service training.
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import us.jubat.classifier.LabeledDatum;
import us.jubat.common.Datum;

/**
 *
 * @author Ioannis Nikolaou
 * @version Final
 * emal: inikolao@hotmail.com
 * AM 4504
 * Diploma Project Part 1
 * Jubatus Classification Service
 * 
 * This service has build with Spring Boot Libraries for the RestFul
 * including Jubatus framework as a Classification Core
 * 
 * 
 */
public class teacherMan {
 //This is an Object which is responsible for the Classification Server Training
 //His mission is to define a Jubatus Classification Client and through this client 
//to connect to Jubatus Classification Server. Using this client he sends training data 
//to Jubatus Classification Server.
    serverclasy server;//This is a stuctural Object we have create and contains all the logic about the definition and the logic of Jubatus Classification client
    //We must say here that the Classification server is a part of the service that is not included in this Jar and it is started separately.
    //However for clarity reasons in the entire project we assume that when we speak about the Jubatus Anomaly client we speak about the server and thats why
    //we use names that indicate to server meanwhile we refer to Client.
    String file;//This is the string variable where we keep the file name that contains the training data
    String name;//This is the string variable where we keep an optional string we sould put in the server as id string.
    
    
    public teacherMan(serverclasy server,String file)
    {//this is the constructor of the object teachermMan
     //It has an serveranom Object, a string that refers to the file from we make the training and an id name about the server.
     //Once it is called sets up the Jubatus Anomaly client and start the training.
        this.server=server;//initialise the object
        this.file=file;
        this.name=server.name;
        teaching();//This is the method that performs the Jubatus Server Training.
    
    }
    public void getStatus()
    {
    //future method. The thought there can be to return the teaching status or the server status.
    }
    private void teaching()
    {//Here we define the method that performs the Jubatus Server Training.
        String line = null;//this is a varaiable we use in file reading. Represents one line of the file.
        String [] elemval;// this is a 2D array we use in file reading. As the form of the file we accept in each is "Type","Value"
        //this array is used to separate the type and the value for each line.
        final BufferedReader br;// This is a Buffer we use to read the file with the training data.
        List<LabeledDatum> dataTotrain=new ArrayList(); // This is a list of LabeledDatum. The Labeled Datum stuct is an object which is defined
        //in the Jubatus Framework and it is used in Classification for training. The logic here is that we send a value=<value> Tuple with a label 
        //to indicate the type of this <value>. So at this time the Classification Server knows the type of the values he gets and prepares his learning model.
        filecontroler flk=new filecontroler("trainDat");//We use the filecontroler object which is defined in Utilities Package in order to read the contents of the trainDat foldel.
        List<String> files=flk.getFileNamsNoDat();//We use a method of the filecontroler object in order to get a list of existing types. We assume here that in tha 
        //trainDat folder exist an number of files each for every type of clasification data and a file with mixed data. All these files are used for training the Service.
        int ik=0;//help integer. It is used to the for loop that helps to make the console messages more user friendly and show the training progress.
        int filenodatsz=files.size();//help integer. It is used to get the number of existing types of Classification.
        int help3=0;//This is an indeger that indicates how much lines of the trianing file we send each time.
        // After many expiraments we find out that even if we was trying to keep the training stream open the Classification server was keep closing the conection
        // in a random way due to a messages library that is based. This is a known issue from the Jubatus develop team and they recommend to close the training stream.
        // Our issue here that we used for training an enormous mixed type data set and the stream was closing from the server before we manage to send all the data.
        // For an unknown reason for us and for safety reasons according to the developer of the message library the threads are pooled randomly and the result is to close the connection.
        // So we decided to sent to server for training small vectors from 5 lines each time during the training file reading. We dont know about the coreectness of this solution and how does it affects
        // the whole service. However it seems that works.
        try {
            System.out.println("Start teaching. Type: "+file);//We sent a info message to console to inform that the training process is starting with the particular file.
            br = new BufferedReader(new FileReader("trainDat/"+file));//This file must be in a folder named trainDat. Here we define a reader we use to read the file with the training data.
            while ((line = br.readLine()) != null) {//here we start the reading of the training file.
                //while we ane not in the end of file we keep reaging from file.
                elemval = line.split(",");//we separate the type and the value for each line of the training file.
                help3++;//we count the files lines in order to create the small training vector we want to send.
                if(help3<5){// we send a vector every 5 lines.
                dataTotrain.add(makeTrainDatum(elemval[0],"value",Double.parseDouble(elemval[1])));//We add the value and the type of the value to the struct named Label Datum. The form is <type> :(key,value)
                //The labeled Datum is added to list in order to create the small vector
                }
                else if(help3==5){help3=0;// the size of the vector is in the limit so its time to send it.
                server.client.train(dataTotrain);//we send the vector for train to add it in the train model.
                dataTotrain.clear();//we clear the list of the vector in ordre to create the new vector.
                }
                for(ik=0;ik<filenodatsz;ik++)// This is for loop which implements a logic for user friendly console messages which indicate the progress of the training.
                {
                    if(elemval[0].contains(files.get(ik))==true)// So if the type of the line we have read is the same with one of the type in the list so we training this type now
                    {// and we sent an analogous message to the console
                        System.out.println("Now Talking about "+elemval[0]);//--> We talking about <Type>
                        files.remove(ik);//then we remove the type from the list because the training with that type wiil not happen again 
                        filenodatsz--;// and we reduce the size of the for loop because we removed a type from the list.
                   }
                
                }
            }
            br.close();//close the file reader.
            dataTotrain.clear();//safety line. we clear the vector we use to send train values. It has no such considerable meaning here because we use vectors
            // of 5 lines. However is much more important in bigger sizes in order to clear memory space.
            
        } catch (FileNotFoundException ex) {//handling the case we can not find the selected data file
            System.out.println("File Not Found Error.");//sending a relative error message to console.->"File Not Found Error."
            System.out.println("Teaching Process got null");//sending a error message to console that training procedure failed.->"Teaching Process got null"
            System.exit(0);//terminate the program.
        } catch (IOException ex) {//handling the case we have an input output error
            System.out.println("File Reader Error.");//sending a relative error message to console.->"File Reader Error."
            System.exit(0);//terminate the program.
        }catch (NumberFormatException ex) {//handling the case that the line we have read is not formatted properly and the number we expect contains unexpected symbols.
            System.out.println("Number Format Error.");//sending a relative error message to console.->"Number Format Error."
            System.out.println(line);// say the line that the problem occurs
            System.out.println(ik);// say the for loop state.
            System.out.println(file);// say the file that the problem occurs
            System.exit(0);//terminate the program.
        }
        //server.client.train(dataTotrain);
        //This is the position that this line should be based on the Jubatus Flamework examples 
        //that indicates the way of the training. However expiraments showed that this is imposible with the train data we use because there are too big
        //even if the Devs of the framework claim that there is no problem. The training way we finally used was descibed above.
    }
    private static Datum createdomeTosent(String key,double value)
    {//this is a method we can use in order to create the Jubatus comunication struct named DATUM.
         Datum datum = new Datum();//We define a new Jubatus comunication struct Datum
         return datum.addNumber(key, value);//And we return it.
    }
    private static LabeledDatum makeTrainDatum(String key,String valou,double value) {
    // this is a method we use to create LabeledDatum. This LabeledDatum stuct is a Jubatus Framework form we use for training.
    // this form requires a Datum stuct. That's the reason we call the method createdomeTosent in return statement.
        return new LabeledDatum(key,createdomeTosent(valou,value));// we return the LabeledDatum.
    }
}
