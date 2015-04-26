<?php
namespace FSF\Routing;

use FSF\Routing\Route;

class Router
{

    /** @var Route[] List of routes to check on routing */
    private $routes = array();

    /** @var  Request */
    private $originalRequest;

    /**
     * Construct - may define routes to instantiate
     */
    public function __construct()
    {
    }

    /**
     * @param Request $request
     *
     * @return $this
     */
    private function setOriginalRequest(Request $request)
    {
        $this->originalRequest = clone $request;

        return $this;
    }

    /**
     * Retrieve original request
     *
     * @return Request
     */
    public function getOriginalRequest()
    {
        return $this->originalRequest;
    }

    /**
     * Clear all routes defined
     *
     * @return $this
     */
    public function clearRoutes()
    {
        $this->routes = array();

        return $this;
    }

    /**
     * Add a route in stack
     *
     * @param Route $route
     *
     * @return $this
     */
    public function addRoute(Route $route)
    {
        array_push($this->routes, $route);

        return $this;
    }


    /**
     * @param Request $request
     *
     * @return Route|null
     */
    public function getRoute(Request $request)
    {
        $this->setOriginalRequest($request);
        $redispatch = false;
        $routeSelected = null;
        do {
            foreach ($this->routes as $route) {
                if ($route->setRequest($request)->canHandle()) {
                    $route->handle();
                    $redispatch = $route->hasToBeReDispatched();
                    $routeSelected = $route;
                    break;
                }
            }
        } while ($redispatch);

        return $routeSelected;
    }

}