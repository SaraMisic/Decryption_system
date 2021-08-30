public class Main {
    public static void main(String[] args) {

        if(args.length !=3)
        {
            System.out.println("Nisu uneti svi podaci u komandnu liniju!\n");
            return;
        }

        String pathToInput = args[2];
        String serverAdress = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try {
            new Client(serverAdress,portNumber,pathToInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
