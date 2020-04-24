package Model;

public class Path {
    private int width;
    private int[] pos;

    public Path(int[] pos, int width){
        this.pos = pos;
        this.width = width;
    }

    public int get_ord(int x){return pos[x];}

    public int[] next_pos(int pos_x, int pos_y, int speed){
        int offset = pos_y-pos[pos_x];
        System.out.println("next_pos");
        System.out.println(offset);
        System.out.println(pos_x);
        System.out.println(speed);
        System.out.println(Math.max(pos[Math.max(pos_x-speed,0)]+offset, 0));
        System.out.println();
        return new int[]{Math.max(pos_x-speed, 0), Math.max(pos[Math.max(pos_x-speed,0)]+offset, 0)};
    }

    public int get_width(){return width;}
}
