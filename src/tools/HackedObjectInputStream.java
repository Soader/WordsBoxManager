package tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import core.ManagerWindow;

public class HackedObjectInputStream extends ObjectInputStream {

	public HackedObjectInputStream(InputStream in) throws IOException {
		super(in);
	}

	@Override
	protected ObjectStreamClass readClassDescriptor() throws IOException,
			ClassNotFoundException {
		ObjectStreamClass resultClassDescriptor = super.readClassDescriptor();

		if (resultClassDescriptor.getName().equals("Todays")) {
			resultClassDescriptor = ObjectStreamClass.lookup(core.Todays.class);
			if(ManagerWindow.DEBUG)	
				System.out.println("Todays ClassDescriptor has been changed to core.Todays");
		}
		if (resultClassDescriptor.getName().equals("Word")) {
			resultClassDescriptor = ObjectStreamClass.lookup(core.Word.class);
			if(ManagerWindow.DEBUG)
				System.out.println("Word ClassDescriptor has been changed to core.Word");
		}
		if (resultClassDescriptor.getName().equals("Language")) {
			resultClassDescriptor = ObjectStreamClass
					.lookup(core.Language.class);
			if(ManagerWindow.DEBUG)
				System.out.println("Language ClassDescriptor has been changed to core.Language");
		}

		return resultClassDescriptor;
	}
}