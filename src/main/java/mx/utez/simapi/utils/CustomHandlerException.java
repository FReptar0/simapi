package mx.utez.simapi.utils;

public class CustomHandlerException {
    public static String handleException(Exception e) {
        String errorMessage = "";
        switch (e.getClass().getSimpleName()) {
            case "DuplicateKeyException":
                errorMessage = "Error: El registro ya existe en la base de datos";
                break;
            case "CannotAcquireLockException":
                errorMessage = "Error: No se pudo adquirir el bloqueo para actualizar el registro";
                break;
            case "DataAccessException":
                errorMessage = "Error: Problema de acceso a datos";
                break;
            case "DataIntegrityViolationException":
                errorMessage = "Error: Violación de integridad de datos";
                break;
            case "DataRetrievalFailureException":
                errorMessage = "Error: Problema al recuperar los datos de la base de datos";
                break;
            case "IncorrectResultSizeDataAccessException":
                errorMessage = "Error: Problema al recuperar los datos de la base de datos";
                break;
            case "InvalidDataAccessApiUsageException":
                errorMessage = "Error: Uso no válido de la API de acceso a datos";
                break;
            case "InvalidDataAccessResourceUsageException":
                errorMessage = "Error: Uso no válido de un recurso de acceso a datos";
                break;
            case "OptimisticLockingFailureException":
                errorMessage = "Error: Problema de bloqueo optimista en la actualización del registro";
                break;
            case "PermissionDeniedDataAccessException":
                errorMessage = "Error: Permiso denegado para acceder a los datos";
                break;
            case "RecoverableDataAccessException":
                errorMessage = "Error: Problema de acceso a datos recuperable";
                break;
            case "TransientDataAccessException":
                errorMessage = "Error: Problema de acceso a datos transitorio";
                break;
            default:
                errorMessage = "Error desconocido: " + e.getMessage();
                break;
        }
        return errorMessage;
    }

}
