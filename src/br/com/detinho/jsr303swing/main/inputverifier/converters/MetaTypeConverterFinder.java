package br.com.detinho.jsr303swing.main.inputverifier.converters;

public class MetaTypeConverterFinder {

	public MetaTypeConverter find(Class<?> typeToConvert) {
		Class<?> converterType = tryToFindTheConverterClass(typeToConvert);
		return tryToInstantiate(converterType);
	}
	
	private Class<?> tryToFindTheConverterClass(Class<?> typeToConvert) {
		String className = getClassName(getClass(), typeToConvert);
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException ex) {
			try {
				className = getClassName(typeToConvert, typeToConvert);
				return Class.forName(className);
			} catch (ClassNotFoundException ex2) {
				throw new MetaTypeConverterNotFoundException(ex.getLocalizedMessage(), ex2);
			}
		}		
	}
	
	private String getClassName(Class<?> theClass, Class<?> typeToConvert) {
		String packageName = theClass.getPackage().getName();
		String className = packageName + "." + typeToConvert.getSimpleName() + "MetaTypeConverter";
		return className;
	}
	
	private MetaTypeConverter tryToInstantiate(Class<?> converterType) {
		try {
			return (MetaTypeConverter)converterType.newInstance();
		} catch (InstantiationException ex) {
			throw new MetaTypeNotAConverterException(ex.getLocalizedMessage(), ex);
		} catch (IllegalAccessException ex) {
			throw new MetaTypeNotAConverterException(ex.getLocalizedMessage(), ex);
		} catch (ClassCastException ex) {
			throw new MetaTypeNotAConverterException(ex.getLocalizedMessage(), ex);
		}		
	}
	
}
