<?php
require_once 'String.php';
use FSF\String as String;

// Autoload Registering for PHPUnit
spl_autoload_register('__autoload');


/***************************
 * PSR 4 Autoload
 *
 * https://github.com/php-fig/fig-standards/blob/master/accepted/PSR-4-autoloader-examples.md
 * http://www.php-fig.org/psr/psr-4/fr/
 **************************/

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

spl_autoload_register(function ($class) {
    // project-specific namespace prefix
    $prefix = 'FSF\\';

    // base directory for the namespace prefix
    $base_dir = ROOT . '/system/';

    // does the class use the namespace prefix?
    $len = strlen($prefix);
    if (strncmp($prefix, $class, $len) !== 0) {
        // no, move to the next registered autoloader
        return;
    }

    // get the relative class name
    $relative_class = substr($class, $len);

    // replace the namespace prefix with the base directory, replace namespace
    // separators with directory separators in the relative class name, append
    // with .php
    $file = $base_dir . str_replace('\\', '/', $relative_class) . '.php';

    // if the file exists, require it
    if (file_exists($file)) {
        require_once $file;
    }
});
spl_autoload_register(function ($class) {
    // project-specific namespace prefix
    $prefix = 'APP\\';

    // base directory for the namespace prefix
    $base_dir = ROOT . '/application/';

    // does the class use the namespace prefix?
    $len = strlen($prefix);
    if (strncmp($prefix, $class, $len) !== 0) {
        // no, move to the next registered autoloader
        return;
    }

    // get the relative class name
    $relative_class = substr($class, $len);

    // replace the namespace prefix with the base directory, replace namespace
    // separators with directory separators in the relative class name, append
    // with .php
    $file = $base_dir . str_replace('\\', '/', $relative_class) . '.php';

    // if the file exists, require it
    if (file_exists($file)) {
        require_once $file;
    }
});