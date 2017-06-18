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
package clasification.springcore;

/*
This package is based in the example for Restfull service of spring Boot.
We keep in separate package the logic of the service in order to have more clear 
view of the code.
*/

import clasification.utilities.serverclasy;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import us.jubat.classifier.EstimateResult;

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
@RestController
public class controler {
     /*
    Here is the RestFul Controler of the Service. The controler is a HTTP request handler.
    This is defined from the @RestController annotation.
    */
    private final AtomicLong counter = new AtomicLong();//We define an up-counter who represents the id of the result and is also returned with the result.
    //Every instance of Service request initialise an client to the Clasification Server in order to get the answer.
    private List<List<EstimateResult>> results;//This is a list of Tuples that returned from the service of Clasification server. The form of tis list
    //is: <Type> : <Score>. The Score represents the posibility of each type to beening related with the initial given value to the service of
    //Classification. This Type of the list is defined in Jubatus Framework.
    //He re we define the ip of the Clasification Server, the port, the timeout and the name. The name is an optional string wich is an id of the client.
    String iphost="83.212.97.147";//ip of the Classification Server
    String name="classifier";//name/ID of the Classification Server
    int port=9200;//the port of the Classification Server
    int timeout=432000;//timeout of the Classification Server | This value was selected after expiraments because of the stracture of Jubatus frmework
    //we would like to keep the channel active as many time as posible in order to be sure we get the answer or we will achieve the training in a correct way. 
    //This is set because the falmework uses an message library wihch might close the channell in an unexpected manner. So we need this value to be safe here.
    private serverclasy server= new serverclasy(iphost, port, name, timeout);//This is an utility object which designed to enclose all the Classification client logic
    //due to clarity reasons. This object includes the original initialisation of the server, the connection, the function of Classification plus more functions for easiness.
    //More you can see in the Object Definition in Utility Package.
    
    @RequestMapping("/clasify")//This is a Spring Boot annotation which ensures that HTTP requests to /clasify are mapped to coreback method.
    //This  coreback method keeps the resulting information. For more see the object definition.
    public coreback clasify(@RequestParam(value="value", defaultValue="0") double value) {
    //Here we define the Method that keeps the main logic of the restful service. In other words we say tha every request for Classification is been handled from this method.
    //Here we define also the format of the url request. This format can be discovered by the definition of the values inside parentheses.
    //The format is as follows. The input of the Classification service is a single value. So in the url we need to sent only one value for Classification
    //and that we do.
    //This is defined with an @RequestParam anotation.
    //This anotation binds the value of the query string parameter value into the type parameter of the Classification method.
    //This string parameter of the query is explicitly marked as optional by default. If it is an absence the default value of the parameter is given by
    //the defaultValue varaiable.
    //Finally the url format is: /clasify?value="ValueToCheck" The default value here is zero.
        System.out.println("Requesting Clasification....");//We sent an info message to console to give the status of the request. After see this we can expect to get an answer.
        results= server.getResults(value);// Here we start the procedure of Classification. This is the key 
            //line for that procedure due to incorporates core logic about the server communication. Basically we call a method which is defined in the serverclasy object
            // and it is responsible for the classification requests.
        return new coreback(counter.incrementAndGet(),results);//Here we get the response 
        //of the service and thanks to the spring boot library this is encoded in JSON
    }
}
