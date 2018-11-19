package com.mtk.songsOrganizer.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mtk.songsOrganizer.common.annotations.Entity;
import com.mtk.songsOrganizer.common.annotations.IgnoreField;
import com.mtk.songsOrganizer.common.dto.DTOObj;

public class Utils {
	private static Map<String, MongoDatabase> dbMap = new HashMap<>();
	private static Map<String, MongoCollection> collectionMap = new HashMap<>();

	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			return ((String) obj).trim().isEmpty();
		} else if (obj instanceof Short) {
			return (Short) obj == 0;
		} else if (obj instanceof Integer) {
			return (Integer) obj == 0;
		} else if (obj instanceof Long) {
			return (Long) obj == 0;
		} else if (obj instanceof List) {
			return ((List) obj).size() == 0;
		} else if (obj instanceof Map) {
			return ((Map) obj).size() == 0;
		} else if (obj instanceof Set) {
			return ((Set) obj).size() == 0;
		} else if (obj instanceof Object[]) {
			return ((Object[]) obj).length == 0;
		}
		return false;
	}

	public static Object deepClone(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			return obj.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void copyObject(Object fromObj, Object toObj) {
		if (fromObj == null || toObj == null) {
			return;
		}
		Class toClass = toObj.getClass();
		Class fromClass = fromObj.getClass();
		Field[] fields = fromClass.getDeclaredFields();
		if (isEmpty(fields)) {
			return;
		}
		Field tempField;
		for (Field field : fields) {
			try {
				tempField = toClass.getDeclaredField(field.getName());
				field.setAccessible(true);
				tempField.setAccessible(true);
				if (field.getType().equals(tempField.getType())) {
					tempField.set(toObj, field.get(fromObj));
				}
			} catch (NoSuchFieldException e) {

			} catch (IllegalAccessException e) {

			}
		}
	}

	public static void populateDocumentFromDTO(Object object, Document document, Set<String> fieldSet,
			boolean isFilterDoc) {
		if (document == null || object == null) {
			return;
		}
		Class class1 = object.getClass();
		List<Field> fields = getAllSuperClassFields(class1, null);
		List newList, oldList;
		Document tempDoc;
		if (!isEmpty(fields)) {
			for (Field field : fields) {
				field.setAccessible(true);
				if (fieldSet != null && !fieldSet.contains(field.getName())) {
					continue;
				}
				try {
					if (isFilterDoc && isEmpty(field.get(object))) {
						continue;
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (field.getType() == List.class) {
					newList = new ArrayList<>();
					try {
						oldList = (List) field.get(object);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						continue;
					}
					if (!isEmpty(oldList)) {
						for (Object o : oldList) {
							try {
								if (o instanceof DTOObj) {
									tempDoc = new Document();
									populateDocumentFromDTO(o, tempDoc, fieldSet, isFilterDoc);
									newList.add(tempDoc);
								} else {
									newList.add(o);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						document.append(field.getName(), newList);
					}
				} else {
					try {
						if (field.getType() == short.class) {
							document.append(field.getName(), (int) (short) field.get(object));
						} else {
							document.append(field.getName(), field.get(object));
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void populateDTOFromDocument(Document document, Object object) {
		if (document == null || object == null) {
			return;
		}
		Class class1 = object.getClass();
		Field field = null;
		for (String key : document.keySet()) {
			try {
				field = class1.getDeclaredField(key);
			} catch (Exception e) {
				try {
					field = class1.getSuperclass().getDeclaredField(key);
				} catch (Exception e1) {
					field = null;
				}
			}
			if (field != null) {
				field.setAccessible(true);
				if (field.getType() == List.class) {
					try {
						List list = document.get(key, List.class);
						if (!isEmpty(list)) {
							List setList = new ArrayList<>();
							Class objectClass = ((Class) ((ParameterizedType) field.getGenericType())
									.getActualTypeArguments()[0]);
							Object tempObj;
							for (Object o : list) {
								if (objectClass == Short.class) {
									try {
										setList.add(Short.parseShort(o + ""));
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else if (!isEmpty(objectClass.getAnnotatedInterfaces())
										&& objectClass.getAnnotatedInterfaces()[0].getType() == DTOObj.class) {
									tempObj = objectClass.getConstructor().newInstance();
									populateDTOFromDocument((Document) o, tempObj);
									setList.add(tempObj);
								} else {
									setList.add(o);
								}
							}
							if (setList.size() > 0) {
								field.set(object, setList);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						if (field.getType() == short.class) {
							field.set(object, (document.getInteger(key)).shortValue());
						} else {
							field.set(object, document.get(key));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/*
	 * Gets all super-class fields except the ones with the annotation @IgnoreField
	 * Optional : annoCls - to find fields with a specific annotation
	 */
	public static List<Field> getAllSuperClassFields(Class class1, Class annoCls) {
		List<Field> fieldList = new ArrayList<>();
		while (class1.getSuperclass() != null) {
			for (Field field : class1.getDeclaredFields()) {
				if (!field.isAnnotationPresent(IgnoreField.class)
						&& (annoCls == null || field.isAnnotationPresent(annoCls))) {
					fieldList.add(field);
				}
			}
			class1 = class1.getSuperclass();
		}
		if (fieldList.size() == 0) {
			return null;
		}
		return fieldList;
	}

	/* DB CONNECTION UTILS */
	private static void connectToDb(String dbName) {
		MongoClient mongoClient = new MongoClient(External.mongoHost, 27017);
		if (mongoClient == null) {
			mongoClient = new MongoClient();
			if (mongoClient == null) {
				return;
			}
		}

		dbMap.put(dbName, mongoClient.getDatabase(dbName));
	}

	public static MongoCollection getCollection(Class cls) {
		Entity entity = (Entity) cls.getAnnotation(Entity.class);
		if (entity == null) {
			return null;
		}

		MongoDatabase mongoDatabase = dbMap.get(entity.dbName());
		if (mongoDatabase == null) {
			connectToDb(entity.dbName());
		}
		mongoDatabase = dbMap.get(entity.dbName());
		if (mongoDatabase == null) {
			return null;
		}
		MongoCollection mongoCollection = collectionMap.get(entity.dbName() + "_" + entity.collectionName());
		if (mongoCollection == null) {
			mongoCollection = mongoDatabase.getCollection(entity.collectionName());
			collectionMap.put(entity.dbName() + "_" + entity.collectionName(), mongoCollection);
		}
		return mongoCollection;
	}
}
