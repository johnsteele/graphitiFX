package myfxviewpart.views;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.ui.IFileEditorMapping;

import at.bestsolution.efxclipse.runtime.workbench3.FXViewPart;

public class MyViewPart extends FXViewPart {

	private ResourceSet resourceSet = null;
	
	@Override
	protected Scene createFxScene() {

		resourceSet = new ResourceSetImpl();
		
		IPath path = new Path("/sample1/C1.diagram");
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		Diagram diagram = getDiagramFromFile(file, resourceSet);
		
		FxDiagram fxDiagram = new FxDiagram(diagram);

//		FxRectangle rectangle = new FxRectangle(70, 70, 100, 100, Color.RED);
//		fxDiagram.addShape(rectangle);
//
//		FxRectangle rectangle4 = new FxRectangle(40, 40, 20, 20, Color.BLACK);
//		rectangle.addShape(rectangle4);
//
//		FxRectangle rectangle2 = new FxRectangle(120, 120, 100, 100,
//				Color.GREEN);
//		fxDiagram.addShape(rectangle2);
//
//		FxRectangle rectangle3 = new FxRectangle(10, 10, 10, 10, Color.YELLOW);
//		rectangle2.addShape(rectangle3);
//
		return new Scene(fxDiagram);

	}

	@Override
	protected void setFxFocus() {
		// button.requestFocus();
	}
	
	@Override
	public void dispose() {
		resourceSet = null;
		super.dispose();
	}

	private Diagram getDiagramFromFile(IFile file, ResourceSet resourceSet) {
		// Get the URI of the model file.
		URI resourceURI = getFileURI(file);
		// Demand load the resource for this file.
		Resource resource;
		try {
			resource = resourceSet.getResource(resourceURI, true);
			if (resource != null) {
				// does resource contain a diagram as root object?
				URI diagramUri = mapDiagramFileUriToDiagramUri(resourceURI);
				EObject eObject = resource.getEObject(diagramUri.fragment());
				if (eObject instanceof Diagram)
					return (Diagram) eObject;
			}
		} catch (WrappedException e) {
			e.printStackTrace();
		}
		return null;
	}

	private URI getFileURI(IFile file) {
		String pathName = file.getFullPath().toString();
		URI resourceURI = URI.createPlatformResourceURI(pathName, true);
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceURI = resourceSet.getURIConverter().normalize(resourceURI);
		return resourceURI;
	}

	private URI mapDiagramFileUriToDiagramUri(URI diagramFileUri) {
		return diagramFileUri.appendFragment("/0"); //$NON-NLS-1$
	}
}