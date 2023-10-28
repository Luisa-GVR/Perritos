import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.TimerTask;

public class ImageView extends JFrame {
    private ArrayList<String> imageUrls;
    private int currentImageIndex = 0;
    private JLabel imageLabel;
    private int intervalInMilliseconds;
    private static String BASE_URL = "https://dog.ceo/api/breeds/image/random/";

    public ImageView(ArrayList<String> imageUrls, int intervalInMilliseconds) {
        super();
        this.imageUrls = imageUrls;
        this.intervalInMilliseconds = intervalInMilliseconds;
        initUI();
        showNextImage();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        imageLabel = new JLabel();
        add(imageLabel, BorderLayout.CENTER);
        setTitle("Image Viewer");
        setSize(800, 600);
        setLocationRelativeTo(null);

        Timer timer = new Timer(intervalInMilliseconds, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextImage();
            }
        });
        timer.start();
    }

    private void showNextImage() {
        if (imageUrls != null && !imageUrls.isEmpty()) {
            String imageUrl = imageUrls.get(currentImageIndex);
            try {
                URL imgUrl = new URL(imageUrl);
                ImageIcon imageIcon = new ImageIcon(imgUrl);
                imageLabel.setIcon(imageIcon);

                int width = imageIcon.getIconWidth();
                int height = imageIcon.getIconHeight();
                setSize(width, height);

                setLocationRelativeTo(null);

                currentImageIndex = (currentImageIndex + 1) % imageUrls.size();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public static DogApi query(int n){
        ArrayList<String> validBreeds = new ArrayList<>(Arrays.asList(
                "random", "affenpinscher", "african", "airedale", "akita", "appenzeller", "australian",
                "basenji", "beagle", "bluetick", "borzoi", "bouvier", "boxer", "brabancon", "briard", "buhund",
                "bulldog", "bullterrier", "cattledog", "cavapoo", "chihuahua", "chow", "clumber", "cockapoo",
                "collie", "coonhound", "corgi", "cotondetulear", "dachshund", "dalmatian", "dane", "deerhound",
                "dhole", "dingo", "doberman", "elkhound", "entlebucher", "eskimo", "finnish", "frise",
                "germanshepherd", "greyhound", "groenendael", "havanese", "hound", "husky", "keeshond", "kelpie",
                "komondor", "kuvasz", "labradoodle", "labrador", "leonberg", "lhasa", "malamute", "malinois",
                "maltese", "mastiff", "mexicanhairless", "mix", "mountain", "newfoundland", "otterhound", "ovcharka",
                "papillon", "pekinese", "pembroke", "pinscher", "pitbull", "pointer", "pomeranian", "poodle", "pug",
                "puggle", "pyrenees", "redbone", "retriever", "ridgeback", "rottweiler", "saluki", "samoyed", "schipperke",
                "schnauzer", "segugio", "setter", "sharpei", "sheepdog", "shiba", "shihtzu", "spaniel", "spitz",
                "springer", "stbernard", "terrier", "tervuren", "vizsla", "waterdog", "weimaraner", "whippet", "wolfhound"
        ));

        String selectedBreed = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la raza del perro:",
                "Selección de Raza",
                JOptionPane.QUESTION_MESSAGE,
                null,
                validBreeds.toArray(),
                validBreeds.get(0)
        );

        if (selectedBreed.equals("random")){
            BASE_URL = "https://dog.ceo/api/breeds/image/random/";
        } else {
            BASE_URL = "https://dog.ceo/api/breed/" + selectedBreed + "/images/random/";
        }


        DogApi response = null;
        try{
            URL u = new URL(BASE_URL + n);

            BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
            String buffer;
            StringBuilder jsonText = new StringBuilder();
            while ((buffer = in.readLine()) != null){
                jsonText.append(buffer);
            }
            in.close();
            Gson gson = new Gson();
            response = gson.fromJson(jsonText.toString(), DogApi.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static void main(String[] args) {
        int n = new Random().nextInt(11) + 10;
        int intervalInSeconds = 15;


        DogApi response = query(n);

        ArrayList<String> urls = response.getMessage();

        int finalIntervalInSeconds = intervalInSeconds;
        EventQueue.invokeLater(() -> {
            JFrame ex = new ImageView(urls, finalIntervalInSeconds * 1000);
            ex.setVisible(true);
        });
    }

}