//
//  AppDelegate.m
//  Permis Bateau
//
//  Created by Alexandre Dubois on 15/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import "AppDelegate.h"

@interface AppDelegate ()

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
   /* NSManagedObjectContext *context = [self managedObjectContext];
    Theme *theme = [NSEntityDescription
                                      insertNewObjectForEntityForName:@"Theme"
                                      inManagedObjectContext:context];
    
    theme.numero= [NSNumber numberWithInt:1];
    theme.nom = @"Theme 1 - Signalisation";
    
    Theme *theme2 = [NSEntityDescription
                    insertNewObjectForEntityForName:@"Theme"
                    inManagedObjectContext:context];
    
    theme2.numero= [NSNumber numberWithInt:2];
    theme2.nom = @"Theme 2 - Signaux sonores";
    

    Theme *theme3 = [NSEntityDescription
                     insertNewObjectForEntityForName:@"Theme"
                     inManagedObjectContext:context];
    
    theme3.numero= [NSNumber numberWithInt:3];
    theme3.nom = @"Theme 3 - Feux ";
    
    
    Serie *serie1 =  [NSEntityDescription
                      insertNewObjectForEntityForName:@"Serie"
                      inManagedObjectContext:context];
    serie1.nom = @"Serie numéro 1";
    serie1.theme = theme;
    serie1.numero = [NSNumber numberWithInt:1];
    
    Serie *serie2 =  [NSEntityDescription
                      insertNewObjectForEntityForName:@"Serie"
                      inManagedObjectContext:context];
    serie2.nom = @"Serie numéro 2";
    serie2.theme = theme;
    serie2.numero = [NSNumber numberWithInt:2];
    
    
    Serie *serie3 =  [NSEntityDescription
                      insertNewObjectForEntityForName:@"Serie"
                      inManagedObjectContext:context];
    serie3.nom = @"Serie numéro 1";
    serie3.theme = theme2;
    serie3.numero = [NSNumber numberWithInt:3];

    
    
    serie2.nom = @"Serie numéro 2";
    
    serie3.nom = @"Serie numéro 3";
    
    
    
    
    Question *question = [NSEntityDescription
                    insertNewObjectForEntityForName:@"Question"
                    inManagedObjectContext:context];
    
    question.serie = serie1;
    question.numero= [NSNumber numberWithInt:1];
    question.enoncer = @"enoncer 1 de la première quenAAAAAAAAAAAAAAAAAAAAaaaaaaaeazeazdaezncozrnvozrnpnabzpfbnazofnaeronfôarbfoazbrnfoaebro^baezro^fnzrofnazof,azoe,foaze faz ofnaz";
    question.theme = theme;
    question.image = @"balise_test.jpg";
    question.reponseA = @"Reponse A aze";
    question.reponseB = @"Reponse B zeazeeeee";
    question.reponseC = @"Reponse C ppppp";
    question.reponseD = @"Reponse D poipupu";
    question.reponseCorrectA = [NSNumber numberWithBool:NO];
    question.reponseCorrectB = [NSNumber numberWithBool:YES];
    question.reponseCorrectC = [NSNumber numberWithBool:YES];
    question.reponseCorrectD = [NSNumber numberWithBool:YES];

    
    Question *question2 = [NSEntityDescription
                          insertNewObjectForEntityForName:@"Question"
                          inManagedObjectContext:context];
    question2.numero= [NSNumber numberWithInt:2];
    question2.serie = serie1;
    question2.enoncer = @"enoncer 2 d";
    question2.image = @"valider.png";
    question2.theme = theme;
    question2.reponseA = @"Reponse A 2";
    question2.reponseB = @"Reponse B 2zeazeeeee";
    question2.reponseC = @"Reponse C 2ppppp";
    question2.reponseD = @"Reponse D 2poipupu";
    question2.reponseCorrectA = [NSNumber numberWithBool:NO];
    question2.reponseCorrectB = [NSNumber numberWithBool:YES];
    question2.reponseCorrectC = [NSNumber numberWithBool:YES];
    question2.reponseCorrectD = [NSNumber numberWithBool:YES];
    
    Question *question3 = [NSEntityDescription
                          insertNewObjectForEntityForName:@"Question"
                          inManagedObjectContext:context];
    
    question3.numero= [NSNumber numberWithInt:3];
    question3.enoncer = @"enoncer 3 ";
    question3.theme = theme2;
    question3.serie = serie2;
    
    Question *question4 = [NSEntityDescription
                          insertNewObjectForEntityForName:@"Question"
                          inManagedObjectContext:context];
    
    question4.numero= [NSNumber numberWithInt:4];
    question4.enoncer = @"enoncer 4 ";
    question4.theme = theme3;
    question4.serie = serie3;
    
    NSError *error;
    if (![context save:&error]) {
        NSLog(@"Whoops, couldn't save: %@", [error localizedDescription]);
    }
    */

    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    // Saves changes in the application's managed object context before the application terminates.
    [self saveContext];
}

#pragma mark - Core Data stack

@synthesize managedObjectContext = _managedObjectContext;
@synthesize managedObjectModel = _managedObjectModel;
@synthesize persistentStoreCoordinator = _persistentStoreCoordinator;

- (NSURL *)applicationDocumentsDirectory {
    // The directory the application uses to store the Core Data store file. This code uses a directory named "Cap-Horn-e-cole-de-croisie-re.Permis_Bateau" in the application's documents directory.
    return [[[NSFileManager defaultManager] URLsForDirectory:NSDocumentDirectory inDomains:NSUserDomainMask] lastObject];
}

- (NSManagedObjectModel *)managedObjectModel {
    // The managed object model for the application. It is a fatal error for the application not to be able to find and load its model.
    if (_managedObjectModel != nil) {
        return _managedObjectModel;
    }
    NSURL *modelURL = [[NSBundle mainBundle] URLForResource:@"Permis_Bateau" withExtension:@"momd"];
    _managedObjectModel = [[NSManagedObjectModel alloc] initWithContentsOfURL:modelURL];
    return _managedObjectModel;
}

- (NSPersistentStoreCoordinator *)persistentStoreCoordinator {
    // The persistent store coordinator for the application. This implementation creates and return a coordinator, having added the store for the application to it.
    if (_persistentStoreCoordinator != nil) {
        return _persistentStoreCoordinator;
    }
    
    // Create the coordinator and store
    
    _persistentStoreCoordinator = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:[self managedObjectModel]];
    NSURL *storeURL = [[self applicationDocumentsDirectory] URLByAppendingPathComponent:@"Permis_Bateau.sqlite"];
    NSError *error = nil;
    NSString *failureReason = @"There was an error creating or loading the application's saved data.";
    if (![_persistentStoreCoordinator addPersistentStoreWithType:NSSQLiteStoreType configuration:nil URL:storeURL options:nil error:&error]) {
        // Report any error we got.
        NSMutableDictionary *dict = [NSMutableDictionary dictionary];
        dict[NSLocalizedDescriptionKey] = @"Failed to initialize the application's saved data";
        dict[NSLocalizedFailureReasonErrorKey] = failureReason;
        dict[NSUnderlyingErrorKey] = error;
        error = [NSError errorWithDomain:@"YOUR_ERROR_DOMAIN" code:9999 userInfo:dict];
        // Replace this with code to handle the error appropriately.
        // abort() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
        NSLog(@"Unresolved error %@, %@", error, [error userInfo]);
        abort();
    }
    
    return _persistentStoreCoordinator;
}


- (NSManagedObjectContext *)managedObjectContext {
    // Returns the managed object context for the application (which is already bound to the persistent store coordinator for the application.)
    if (_managedObjectContext != nil) {
        return _managedObjectContext;
    }
    
    NSPersistentStoreCoordinator *coordinator = [self persistentStoreCoordinator];
    if (!coordinator) {
        return nil;
    }
    _managedObjectContext = [[NSManagedObjectContext alloc] init];
    [_managedObjectContext setPersistentStoreCoordinator:coordinator];
    return _managedObjectContext;
}

#pragma mark - Core Data Saving support

- (void)saveContext {
    NSManagedObjectContext *managedObjectContext = self.managedObjectContext;
    if (managedObjectContext != nil) {
        NSError *error = nil;
        if ([managedObjectContext hasChanges] && ![managedObjectContext save:&error]) {
            // Replace this implementation with code to handle the error appropriately.
            // abort() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
            NSLog(@"Unresolved error %@, %@", error, [error userInfo]);
            abort();
        }
    }
}

@end
