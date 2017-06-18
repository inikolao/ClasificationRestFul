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

import java.util.List;
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
public class coreback {
     /*
    Here is the RestFul "Core" of the Service. This core is an information keeper.
    This object keeps the resulting information from the anomaly detection requests.
    */
    private final long id;//This is the result id. A counter who is increasing by one in every result.
    private final List<List<EstimateResult>> results;//This is the variable that keeps a list of Tuples that returned from the service of Clasification server. The form of tis list
    //is: <Type> : <Score>. The Score represents the posibility of each type to beening related with the initial given value to the service of
    //Classification. This Type of the list is defined in Jubatus Framework.
    public coreback(long id,List<List<EstimateResult>> Score) {//This is the constractor of the object Coreback
        //Here we create an object Coreback with the given parameters as described above.
        this.id = id;
        this.results=Score;
    }
    public long getId() {//A Method which returns the value of the id. As we said the id is the result's id.
        return id;
    }
 
    public List<List<EstimateResult>> getResults() {//A Method which returns the Classification results we keep inside this Coreback object.
        return results;
    }
}
