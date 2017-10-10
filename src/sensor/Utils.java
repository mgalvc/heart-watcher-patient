/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

/**
 *
 * @author matheus
 */
public class Utils {
    private static String[] names = new String[] {
        "Emma",
	"Noah",
	"Olivia",
	"Liam",
	"Sophia",
	"Mason",
	"Ava",
	"Jacob",
	"Isabella",
	"William",
	"Mia",
	"Ethan",
	"Abigail",
	"James",
	"Emily",
	"Alexander",
	"Charlotte",
	"Michael",
	"Harper",
	"Benjamin",
	"Madison",
	"Elijah",
	"Amelia",
	"Daniel",
	"Elizabeth",
	"Aiden",
	"Sofia",
	"Logan",
	"Evelyn",
	"Matthew"
    };
    
    public static String getName(int i) {
        return names[i];
    }
    
    public static int getNamesListLength() {
        return names.length;
    }
    
}
