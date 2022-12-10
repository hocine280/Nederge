<?php 
/**
 * Fichier autoload.php permettant de charger les classes automatiquement
 * @author HADID Hocine
 * @version 1.0
 */

/**
 * Fonction d'autoload
 * @param string $className Nom de la classe à charger
 */
spl_autoload_register(function($class_name){

    $paths = array(
        join(DIRECTORY_SEPARATOR, [__DIR__]), 
        join(DIRECTORY_SEPARATOR, [__DIR__, '..' ,'app']),
        join(DIRECTORY_SEPARATOR, [__DIR__, '..' ,'app', 'controllers']),
        join(DIRECTORY_SEPARATOR, [__DIR__, '..' ,'app', 'models']),
        join(DIRECTORY_SEPARATOR, [__DIR__, '..' ,'app', 'rules']),
        join(DIRECTORY_SEPARATOR, [__DIR__, '..']),
        join(DIRECTORY_SEPARATOR, [__DIR__, '..' , 'resources', 'views']),
    );

    foreach($paths as $path){
        $file = join(DIRECTORY_SEPARATOR, [$path, $class_name.'.php']) ;
        if(file_exists($file))
            return require_once $file;
    }
}); 

