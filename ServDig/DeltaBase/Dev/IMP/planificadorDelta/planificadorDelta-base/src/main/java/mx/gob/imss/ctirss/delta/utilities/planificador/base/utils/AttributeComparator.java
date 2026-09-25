package mx.gob.imss.ctirss.delta.utilities.planificador.base.utils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("rawtypes")
public class AttributeComparator implements Comparator {
	private Object attribute;

	public AttributeComparator(Object attribute) {
		this.attribute = attribute;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int compare(Object objetoUno, Object objetoDos) {
		int val = validaNulos(objetoUno, objetoDos);

		if (val != 2) {
			return val;
		}

		Object attrUno;
		Object attrDos;

		try {
			Method method = null;
			String strAttribute = this.attribute.toString();
			int indexPoint = strAttribute.indexOf(".");
			int lenghtStrAttribute = strAttribute.length();

			if (indexPoint != -1) {
				StringBuffer strMethod1 = new StringBuffer();
				String subAux1 = strAttribute.substring(0, indexPoint);
				strMethod1.append("get")
						.append(StringUtils.capitalize(subAux1));

				StringBuffer strMethod2 = new StringBuffer();
				String subAux2 = strAttribute.substring(indexPoint + 1,
						lenghtStrAttribute);
				strMethod2.append("get")
						.append(StringUtils.capitalize(subAux2));

				Method method2 = objetoUno.getClass().getMethod(
						strMethod1.toString(), (Class[]) null);
				Object objetoTres = method2.invoke(objetoUno, null);
				Object objetoCuatro = method2.invoke(objetoDos, null);

				val = validaNulos(objetoTres, objetoCuatro);
				if (val != 2) {
					return val;
				}

				method = objetoTres.getClass().getMethod(strMethod2.toString(),
						(Class[]) null);

				attrUno = method.invoke(objetoTres, null);
				attrDos = method.invoke(objetoCuatro, null);
			} else {
				StringBuffer strMethod = new StringBuffer();
				strMethod.append("get").append(
						StringUtils.capitalize(strAttribute));
				method = objetoUno.getClass().getMethod(strMethod.toString(),
						(Class[]) null);

				attrUno = method.invoke(objetoUno, null);
				attrDos = method.invoke(objetoDos, null);
			}

			if (attrUno instanceof Comparable && attrDos instanceof Comparable) {
				return ((Comparable) attrUno).compareTo(attrDos);
			}

			return attrUno.toString().compareTo(attrDos.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	private int validaNulos(Object objetoUno, Object objetoDos) {
		if (objetoUno == null && objetoDos == null) {
			return 0;
		}
		if (objetoUno == null) {
			return -1;
		}
		if (objetoDos == null) {
			return 1;
		}

		return 2;
	}

	@SuppressWarnings("unchecked")
	public static void sort(List<String> listField,
			List<? extends Object> listObjects) {
		for (int i = 0; i < listField.size(); i++) {
			Collections.sort(listObjects,
					new AttributeComparator(listField.get(i)));

			for (int k = i - 1; k >= 0; k--) {
				Collections.sort(listObjects,
						new AttributeComparator(listField.get(k)));
			}
		}
	}
}
