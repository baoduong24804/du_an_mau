/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.Utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author anhba
 */
public class BcryptPass {

    public static String mahoaMK(String pass) {

        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    public static boolean dichMaHoa(String pass) {

        return BCrypt.checkpw(pass, BCrypt.gensalt());
    }
}
