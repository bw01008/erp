package erp.UI.content;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class AbstractContentPanel<T> extends JPanel {

	public abstract void setItem(T item);
	public abstract T getItem();
	public abstract void validCheck();	//유효성 체크
	public abstract void clearTf();
}
