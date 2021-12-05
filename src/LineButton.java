import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EmptyStackException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LineButton  extends JButton implements ActionListener {

  protected JPanel drawingPanel;
  protected View view;
  private MouseHandler mouseHandler;
  private LineCommand lineCommand;
  private UndoManager undoManager;

  public LineButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Line");
    this.undoManager = undoManager;
    addActionListener(this);
    view = jFrame;
    drawingPanel = jPanel;
    mouseHandler = new MouseHandler();
  }

  public void actionPerformed(ActionEvent event) {
    view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    // Change cursor when button is clicked
    drawingPanel.addMouseListener(mouseHandler);
    drawingPanel.addMouseMotionListener(mouseHandler);
  // Start listening for mouseclicks on the drawing panel
  }

  private class MouseHandler extends MouseAdapter {
    private int pointCount = 0;
    private PreviewLineCommand preview = null;

    public void mouseClicked(MouseEvent event) {
    if (++pointCount == 1) {
        lineCommand = new LineCommand(View.mapPoint(event.getPoint()));
        
        preview = new PreviewLineCommand();
        preview.setLinePoint(View.mapPoint(event.getPoint()));
    } else if (pointCount == 2) {
        undoManager.undo();
        undoManager.beginCommand(lineCommand);
        pointCount = 0;
        lineCommand.setLinePoint(View.mapPoint(event.getPoint()));
        drawingPanel.removeMouseListener(this);
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        undoManager.endCommand(lineCommand);
        preview = null;
      }
    }
    public void mouseMoved(MouseEvent event) {
      // whenever the mouse is moved
      if (preview != null){
        try{
          Command lastCmd = undoManager.getLastCommand();
          if (lastCmd instanceof PreviewLineCommand) undoManager.undo();
        }catch(EmptyStackException e){
          // do nothing. No preview commands to remove
        }
        preview.setLinePoint(View.mapPoint(event.getPoint()));
        lineCommand.setLinePoint(View.mapPoint(event.getPoint()));
        undoManager.beginCommand(preview);
        undoManager.endCommand(preview);
      }
    }

    private class PreviewLineCommand extends LineCommand{}
  }

}