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

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import us.jubat.classifier.ClassifierClient;
import us.jubat.classifier.EstimateResult;
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
//This is an object we define in order to simplify the initialisation and the conection with the Jubatus Classification Server.
//We should say here that in this object we refer to Jubatus Classification Client although it seems that we speak about Jubatus Classification Servers.
//This happens due to simplicity of the code and the main reason is that the Jubatus Classification Server is defined and initialised out of bounds of this deployment.
//We use this Object as structural tool in order to maintain the connections with the Jubatus Classification Server for training and abnormality testing 
//in a simple way.
public class serverclasy {
//For the definition and the setup of a Jubatus Classification Client we should maintain the following variables
    String iphost;//the ip where the server runs in order to comunicate with.
    int port;// the port where the server runs for the same reason
    String name;// and a optional name to keep separate the client streams.(we must define it however.) This name acts as an id of the server/client communication.
    int timeout;//Moreover we need to define an a timeout(integer) to set when the connection times out.
    ClassifierClient client;//This Object is a Jubatus Classification CLient which is the object we need initialise in order to train or make a Classification test.
    //The serverclasy object includes the method which responsible for the Classification test.  This makes this procedure very easy because we have no worries about to which
//server we referred to.
    public serverclasy(String iphost,int port,String name,int timeout)
    {//This is the constructor of serverclasy Object.
        this.iphost=iphost;//the ip of the Object. This corresponds to the ip of the Jubatus Classification Client.
        this.port=port;//the port  of the Object. This corresponds to the port of the Jubatus Classification Client.
        this.name=name;//the name of the Object. This corresponds to the name of the Jubatus Classification Client.
        this.timeout=timeout;//the timeout of the Ojcet. This corresponds to the timeout of the Jubatus Classification Client.
        this.client=setupcl();//this is the Initialization of the Jubatus Classification client. This refers to a method which is defined below.
         
    
    }
    public String getip(){//this is A method we use to get the ip of the serverclasy.
    return this.iphost;
    }
    public String getname(){//this is A method we use to get the name/id of the serverclasy.
    return this.name;
    }
    public int timeout(){//this is A method we use to get the timeout of the serverclasy.
    return this.timeout;
    }
    public int getport(){//this is A method we use to get the port of the serverclasy.
    return this.port; 
    }
    
    private ClassifierClient setupcl()
    {//this is a method we use to initialise the Jubatus Classification Client and furthermore the serveranom Object.
     //Because we use this method in the constructor the method is PRIVATE.
        ClassifierClient client=null;//First we define a null Jubatus Classification Client.
        try{
            System.out.println("Server Client Setup. Type: "+name);//We sent an info message to console to inform about the initialisation.
            client = new ClassifierClient(iphost,port ,name,timeout);//Then we nitialise the Jubatus Classification Client with port,ip,name and timeout.
        
        }
        catch(UnknownHostException ex){//this is an exception that occurs when in the ip or port we set there is not exist an Jubatus Classification Server instance.
            
            System.out.println("Server Client Setup Failed");//We sent an info message to console that "Server Client Setup Failed".
            System.exit(0);//We terminate the execution.
        } 
        return client;//we return the initialized Jubatus Classification Client to variable client of the serveranom Object.
    }
    
    public List<List<EstimateResult>> getResults(double value)
    {//This is the method we define the procedure of Classification test.
     //Here we sent the value and its key(type) and we get the score in a list of EstimateResult. This is a list o tuples with the form <type>,<sore> for each type.
        List<Datum> dataToCheck=new ArrayList();//we create a Jubatus comunication struct named DATUM in a list form with the type(key) and the value 
        dataToCheck.add(createdomeTosent("value",value));//for Classification test, we sent this pair via this struct and we get the Classification score. 
        List<List<EstimateResult>> results=client.classify(dataToCheck);//we send the DATUM and we get the results for the value we send.
        return results;//and we return it.
    }
    
    private static Datum createdomeTosent(String key,double value)
    {//this is a method we can use in order to create the Jubatus comunication struct named DATUM.
         Datum datum = new Datum();//We define a new Jubatus comunication struct Datum          
         return datum.addNumber(key, value);//And we return it.
    }
    
    public void closeCon()
    {//this is a method we close the connection with the server.
    client.getClient().close();
    }
}
