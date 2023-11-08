package Database;

import Api.ApiRetriever;
import Api.Model.Card;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.sql.*;
import java.util.List;

/**
 * This class retrieves data from an API, downloads images, and inserts card information into a database.
 */
public class DatabaseRetriever {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Failed to get a database connection.");
            return;
        }

        try {
            ApiRetriever api = new ApiRetriever();
            //TODO: THIS WILL DOWNLOAD ALL CARDS (13.000+), DO NOT RUN THIS MORE THAN ONCE, FIRST CHECK IF IT'S NEEDED AND IMPLEMENT A METHOD THAT ONLY DOWNLOADS THE NEW CARDS!
            List<Card> cards = api.getCards(null, null, null, null, null, null, null, null, null, null, null, null);

            String absoluteTargetFolder = "E:/personal storage/games/DuelistsDestiny/assets";
            Path targetFolderPath = Paths.get(absoluteTargetFolder);

            ensureFolderExists(targetFolderPath);

            String absoluteTargetFolderImages = absoluteTargetFolder + "/YugiohImages";
            String absoluteTargetFolderCards = absoluteTargetFolder + "/Cards";
            Path targetFolderPathImages = Paths.get(absoluteTargetFolderImages);
            Path targetFolderPathCards = Paths.get(absoluteTargetFolderCards);

            ensureFolderExists(targetFolderPathImages);
            ensureFolderExists(targetFolderPathCards);

            String insertSQL = "INSERT INTO Card (name, type, frame_type, description, pendulum_description, " +
                    "monster_description, atk, def, level, race, attribute, archetype, link_val, link_markers, scale, card_image) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            for (Card card : cards) {
                downloadAndSaveImage(card, targetFolderPathImages);
                setCardDataInPreparedStatement(card, preparedStatement);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            preparedStatement.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Downloads and saves a card image to the specified folder if it does not already exist.
     *
     * @param card           The card to download the image for.
     * @param targetFolder   The folder to save the image to.
     * @throws IOException   If there is an error downloading or saving the image.
     */
    private static void downloadAndSaveImage(Card card, Path targetFolder) throws IOException {
        String imageUrl = card.getCardImages().get(0).getImageUrl();
        String[] urlParts = imageUrl.split("/");
        String fileName = urlParts[urlParts.length - 1];
        Path targetFilePathImages = targetFolder.resolve(fileName);

        if (!Files.exists(targetFilePathImages)) {
            try (InputStream in = new URL(imageUrl).openStream()) {
                Files.copy(in, targetFilePathImages, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image downloaded and saved to: " + targetFilePathImages);
            }
        } else {
            System.out.println("Image already exists: " + targetFilePathImages);
        }
    }

    /**
     * Sets card data in the prepared statement for database insertion.
     *
     * @param card           The card with the data to insert.
     * @param preparedStatement The prepared statement to set the data in.
     * @throws SQLException   If there is an error setting the data.
     */
    private static void setCardDataInPreparedStatement(Card card, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setObject(1, card.getName());
        preparedStatement.setObject(2, card.getType());
        preparedStatement.setObject(3, card.getFrameType());
        preparedStatement.setObject(4, card.getDesc());
        preparedStatement.setObject(5, card.getPendDesc());
        preparedStatement.setObject(6, card.getMonsterDesc());
        preparedStatement.setObject(7, card.getAtk());
        preparedStatement.setObject(8, card.getDef());
        preparedStatement.setObject(9, card.getLevel());
        preparedStatement.setObject(10, card.getRace());
        preparedStatement.setObject(11, card.getAttribute());
        preparedStatement.setObject(12, card.getArchetype());
        preparedStatement.setObject(13, card.getLinkval());
        Object linkMarkers = card.getLinkmarkers();
        preparedStatement.setObject(14, (linkMarkers != null) ? linkMarkers.toString() : null);
        preparedStatement.setObject(15, card.getScale());
        preparedStatement.setObject(16, card.getName()); // Use card name as image filename (without extension)
    }

    /**
     * Ensures that the specified folder exists, creating it if it doesn't.
     *
     * @param folder The folder to ensure its existence.
     * @throws IOException If there is an error creating the folder.
     */
    private static void ensureFolderExists(Path folder) throws IOException {
        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }
    }
}

