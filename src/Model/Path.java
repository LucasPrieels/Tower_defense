package Model;
import java.util.Vector;

public class Path {

    private int width;
     private Vector [] pathway ;


     public Path(Vector [] v){
         this.pathway=v;
     }



    public void initializePath(Vector [] v ){
        pathway = v ;
        for (int i = 0 ; i < v.length -1 ; i++){

            int difference_x = v[i].get(0)  - v[i+1].get(0);// Axe x difference des composantes
            if (difference_x != 0 ){ //utilise x
                for (int z = 0 ; z < abs(difference_x);z++){
                    if (difference_x<0){
                        //faire bouger vers la droite
                    }
                    else{
                        //faire bouger vers la gauche
                    }

                }

            }

            int difference_y = v[i].get(1)  - v[i+1].get(1);// Axe y
            if (difference_y != 0 ){ //utilise y
                for (int z = 0 ; z < abs(difference_y);z++){
                    if (difference_y<0){
                        //faire bouger bas
                    }
                    else(){
                        //faire bouger vers le haut
                    }

                }

            }













        }



    }









    public int get_ord(int x){
        //...
        //Fonction qui renvoit l'ordonnée du chemin correspondnat à l'abscisse x
        return 0; // A retirer quand la fonction est implémentée
    }




    public int get_width(){return width;}
}
