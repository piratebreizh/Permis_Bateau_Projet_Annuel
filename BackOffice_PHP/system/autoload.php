<?php
require_once 'string.php';
use FSF\String as String;

// Autoload Registering for PHPUnit
spl_autoload_register('__autoload');

/**
 * Autoload
 * @param $class_name
 */
function __autoload($class_name)
{
    $class_name = strtolower(String::to_Case($class_name));

    // List of directories
    $classes_path = array(
        __DIR__,
        __DIR__ . '/helper',
        __DIR__ . '/component',
        __DIR__ . '/../lib',
        __DIR__ . '/../application',
        __DIR__ . '/../application/model',
        __DIR__ . '/../application/model/base',
        __DIR__ . '/../application/' . APPLICATION . '/controller',
        __DIR__ . '/../application/' . APPLICATION . '/controller/component',
    );

    // Search of the class
    $include_path = '';
    foreach ($classes_path as $class_path) {
        if (file_exists($class_path . '/' . $class_name . '.php')) {
            $include_path = $class_path . '/' . $class_name . '.php';
            break;
        }
    }

    // if found, include it
    if ($include_path) require_once $include_path;
}