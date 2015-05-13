package view;

public interface IGBallView{
	
	public void runMode();
	public void buildMode();
	public void setFocus();
	public String getSelectedGizmo();
	public String getSelectedFlipper();
	public String triggerAction();
	
}