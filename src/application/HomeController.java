package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

/**
 *
 * @author Rafael
 */
public class HomeController implements Initializable {
    
    @FXML
    private TextField filesDir;
    @FXML
    private Button bt_Organize, bt_Undo, bt_filesDir;
    @FXML
    private CheckBox checkImages, checkDocuments, checkVideos, checkZip, checkMusic, checkOthers, checkAll;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private MenuItem menuExit;
    
    @FXML
    private void handleButtonClick(ActionEvent event) {
        if(event.getSource()==bt_filesDir){
        	File f = fileChooser();
        	if(f!=null) {
        		filesDir.setText(f.getPath());
        	}
        }
        else if(event.getSource()==bt_Organize){
        	Platform.runLater(new Runnable(){
                @Override
                public void run() {
                	organizeFiles();
                }
            });
        }
        
        else if(event.getSource()==bt_Undo){
 
        }
        
        else if(event.getSource()==menuExit){
        	System.exit(0);
        }
    }
    
    private File fileChooser(){
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Selecione o diretório");
        File selectedFile = fileChooser.showDialog(this.filesDir.getScene().getWindow());
        if (selectedFile != null) {
           return selectedFile;
        }
        return null;
    }
    
    private List<String[]> filters(){
		List<String[]> filters = new ArrayList<String[]>();
		filters.add(new String[]{".png|.jpg|.bmp|.jpeg", "Images"});
		filters.add(new String[]{".zip|.rar", "Compactados"});
		filters.add(new String[]{".psd", "Adobe Photoshop"});
		filters.add(new String[]{".bat|.exe|.jar", "Executaveis"});
		filters.add(new String[]{".pdf|.doc|.docx|.txt|.odt", "Documentos"});
		filters.add(new String[]{".mp3", "Musicas"});
		filters.add(new String[]{".mp4|.mov|.mkv|.avi", "Videos"});
		filters.add(new String[]{".lnk", "Atalhos"});
		filters.add(new String[]{".ico", "Icones"});
		return filters;
	}
	
	private void organizeFiles() {
		try {
			String path = filesDir.getText();
			
			File dir = new File(path);
			File[] files = dir.listFiles();
			
			List<String[]> filters = filters();
			
			int p_count = 0;
			
			for(File f: files) {
				String name = f.getName();
				
				if(f.isFile()) {
					int lastDot = name.lastIndexOf(".");
					if(lastDot>0) {
						String ext = name.substring(lastDot);
						boolean moved = false;
						for(String[] s: filters) {
							if(ext.matches(s[0])) {
								new File(path+s[1]).mkdirs();
								moved = f.renameTo(new File(path+"/"+s[1]+"/"+name));
							}
						}
						if(!moved) {
							new File(path+"/Outros").mkdirs();
							f.renameTo(new File(path+"/Outros/"+name));
						}
					}else {
						new File(path+"/Outros").mkdirs();
						f.renameTo(new File(path+"/Outros/"+name));
					}
				}
				p_count++;
				progressBar.setProgress(p_count/files.length);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			showDialog("Error!",e.getMessage(), AlertType.ERROR);
		}
	}
  
    private void showDialog(String title, String message, AlertType type) {
    	Alert alert = new Alert(type);
    	alert.setTitle(title);
    	alert.setHeaderText(message);
    	alert.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }    
    
}
