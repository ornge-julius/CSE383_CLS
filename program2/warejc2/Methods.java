
package warejc2;


public class Methods {
        public int sum(int a,int b) {
                return a+b;
        }

        public int length(String s) {
                return s.length();
        }

        public String append(String a, String b) {
                StringBuffer sb = new StringBuffer();
                sb.append(a);
                sb.append(" length of a=");
                sb.append(a.length());
                sb.append(" ");
                sb.append(b);
                return sb.toString();
        }
}

