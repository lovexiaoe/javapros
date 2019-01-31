public class Inner {
    public int getI(){return i;}
    private int i=0;
    public String getHello(){
        return "hello "+(++i);

    }
}
