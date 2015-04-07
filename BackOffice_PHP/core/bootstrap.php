<?php
// Chargement de la configuration
require_once(ROOT . '/config/config.php');

/**
 * Autochargement de classe
 * @param String $className
 */
function __autoload($className)
{
    if (file_exists(ROOT . '/core/' . $className . '.php')) {
        require_once(ROOT . '/core/' . $className . '.php');
    } else {
        if (file_exists(ROOT . '/application/controllers/' . $className . '.php')) {
            require_once(ROOT . '/application/controllers/' . $className . '.php');
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

/*
 * Dispatcher ou Router ?
 * Request
 * Response
 */
// Routage de la requête
Router::route($url);