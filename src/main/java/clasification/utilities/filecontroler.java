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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class filecontroler {
//Here we define an Object in order to handle(reading,accessing) Files and Folders we use about training.
//We should say that this could be an interface but because we use it only a few times
//we defined as an Object.
    String filename;//this is the filename of the file we would like to handle.
    String folder;//this is the folder we would like to have access to its files.
    
    public filecontroler(String folder,String file)
    {//In this constructor we use all the variables of the Object
     // and we define file and folder we would like to have access.
        this.filename=file;
        this.folder=folder;
    
    }
    
    public filecontroler(String folder)
    {//In this constructor we use only the variable folder of the Object
     // and we define the folder we would like to have access.
        this.folder=folder;
    }
    
    public String fileselect(int sr)
    {// the logic of this Method is that we select the i file
     //from the folder by an integer sr and we get its filename
     // in order to access it.
       List<String> files= getfoldercontents();// we get a list of the names of the containing files. getfoldercontents method is defined at line 88.
       String file;// we define a string that we use ti get the sr-th file name.
       file=files.get(sr);//we select the sr-th file name from the list
        return file;// we return the file name 
    }
    public boolean testfile()
    {//This is a method we define to test if the given filename 
     //is not a matching to file but is finally a folder.
     //Returns TRUE if the given filename is an Directory and not a file
     //and FALSE if the given filename is a file.
        File file= new File(filename);//get the defined filename of the constructor 
        boolean filetest=file.isDirectory();// is a directory?
        return filetest;
    }
    //In this Project we was given a folder with diferent types of datasets. Each file coresponts to a spesific type of values
    //either for training or for testing. One of all these files was containing mixed type of data and was named all.dat.
    //We use either the list of the files, each one for the spesific type of data that respresents, or the file all.dat only.
    //we should say that in this particular logic we have develop it is enough the file with the mixed data to contain the string "all" in
    //its filename in order to be selected or excluded.
    //So we would like to have way to select our experiments these files.
    //That way is represented in the following methods. Each one is for proper use.
    public List<String> getfoldercontents()
    {//This a Method its goal is to return all the filenames that contained to the given folder except the file all.dat.
     //Firstly we define an ArrayList named "contents" in order to associate it with the filenames.
        ArrayList<String> contents= new ArrayList();//this is the list we use to get the contents of the forlder
        File file= new File(folder);//this is the file descriptor we get to access to the given directory.
        boolean isDirectory = file.isDirectory();//We check if the given name that we have assumed that is a directory is finally a directory.
        ArrayList<File> files;//we define an other ArrayList named "files" in order to associate it with the filenames.
        int sz=0;//this is an integer we use to  count the contained files.
        if(isDirectory==true)//if the given name corresponds indeed to a directory
        {
            files = new ArrayList<>(Arrays.asList(file.listFiles()));//we get the files are contained in the given folder in a ArrayList.
            for(sz=0;sz<files.size();sz++)
            {//we search all filesnames in order to exclude the all.dat file.
                if(files.get(sz).getName().contains("all")!=true)
                {//we collect all the files except the all.dat file and we put the in a ArrayList.
                contents.add(files.get(sz).getName());
                }
            
            }
        }
        else
        {//if the given name does not correspond indeed to a directory
            System.out.println("Folder Not Found Error.");// we sent the info message to console "Folder Not Found Error"
            contents=null;//and we set the "contents" list as null. 
        }
    //So if there is not exist a folder with the given name
    //the returned list is null else is all the files that contained in that 
    //particular folder except the all.dat.
        return contents;
    }
    public String getAllData()
    {//This a Method its goal is to return the file all.dat excluding all the other contained files.
     //Firstly we define an String named "contents" in order to associate it with returned file we expect to be the all.dat file.
        ArrayList<String> contents= new ArrayList();//this is the list we use to get the contents of the forlder
        File file= new File(folder);//this is the file descriptor we get to access to the given directory.
        boolean isDirectory = file.isDirectory();//We check if the given name that we have assumed that is a directory is finally a directory.
        ArrayList<File> files;//we define an ArrayList named "files" in order to associate it with the filenames.
        int sz=0;//this is an integer we use to  count the contained files.
        if(isDirectory==true)//if the given name corresponds indeed to a directory
        {
            files = new ArrayList<>(Arrays.asList(file.listFiles()));//we get the files are contained in the given folder in a ArrayList.
            for(sz=0;sz<files.size();sz++)
            {//we search all filesnames in order to exclude all files exept the all.dat file.
                if(files.get(sz).getName().contains("all")==true)
                {//we find and collect the all.dat file.
                contents.add(files.get(sz).getName());//we put it in the contents variable.
                }
            
            }
        }
        else
        {//if the given name does not correspond indeed to a directory
            System.out.println("Folder Not Found Error.");// we sent the info message to console "Folder Not Found Error"
            contents=null;//and we set the "contents" string variable as null. 
        }
    
        return contents.get(0);
        //So if there is not exist a folder with the given name
    //the returned string is null else is all the file all.dat.
    //We have to note here that we the first element of a list of file that contain "all" string in their filenames.
    //we assume that there is only one with that name.
    }
    public int countForderContents()
    {//This is a method which returns the number of the contained files inside the given folder if the given name corresponds indeed to a directory
     //else returns -1 as size.
        int size;//this is the integer that represents the number of contained files.
        File file= new File(folder);//this is the file descriptor we get to access to the given directory.
        boolean isDirectory = file.isDirectory();//We check if the given name that we have assumed that is a directory is finally a directory.
        ArrayList<File> files;//this is a list we use to count the files are contained in the given folder.
        if(isDirectory==true)//if the given name corresponds indeed to a directory
        {
        files = new ArrayList<>(Arrays.asList(file.listFiles()));//we get the files are contained in the given folder in a ArrayList.
        size=files.size();//we get the size of the file list plus one that is the . element
        size--;//we remove the . element.
        }
        else
        {
        size = -1;//if the given name does not correspond indeed to a directory we set an negative size.
        }
        return size;//finally we return the size.
    }
    public List<String> getFileNamsNoDat()
    {//This is a method which returns a list of contained filenames in the given folder in a string form without ".dat". 
        List<String> filenames=new ArrayList();//this is the list we use to get the contents of the forlder
        List<String> filenamesNoDat=new ArrayList();//this is the list we use to  return the contents of the forlder without ".dat".
        filenames=getfoldercontents();// we get a list of the names of the containing files. getfoldercontents method is defined at line 88.
        String[] spliter;// this is a spliter in order to remove the ".dat" from all filenames
        int cnt=0;// help integer in for loop that removes the ".dat" from all filenames
        for(cnt=0;cnt<countForderContents();cnt++)
        {// we scan the list of filenames
            spliter=filenames.get(cnt).split("\\.");// and we split each one in the "."
            filenamesNoDat.add(spliter[0]);//we add the first element of the array that represents the filename without ".dat"
            // in the list we return.
        
        }
        return filenamesNoDat;// we return a list of filenames without ".dat"
    }
}
