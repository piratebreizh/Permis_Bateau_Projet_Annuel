<?php
// Chargement de la configuration
require_once(ROOT . '/config/config.php');

/**
 * Autochargement de classe
 * @param String $className
 */
function __autoload($className)
{
    if (file_exists(ROOT . '/core/' . strtolower($className) . '.php')) {
        require_once(ROOT . '/core/' . strtolower($className) . '.php');
    } else {
        if (file_exists(ROOT . '/application/controllers/' . strtolower($className) . '.php')) {
            require_once(ROOT . '/application/controllers/' . strtolower($className) . '.php');
        } else {
            if (file_exists(ROOT . '/application/models/' . $className . '.php')) {
                require_once(ROOT . '/application/models/' . $className . '.php');
            } else {
                if (file_exists(ROOT . '/core/sql.php')) {
                    require_once(ROOT . '/core/sql.php');
                }
            }
        }
    }
}

// Routage de la requête
Router::route($url); 