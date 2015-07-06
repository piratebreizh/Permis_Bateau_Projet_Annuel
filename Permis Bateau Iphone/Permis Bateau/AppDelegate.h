//
//  AppDelegate.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 15/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreData/CoreData.h>
#import "Theme.h"
#import "Question.h"
#import "Serie.h"
#import "Cours.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (readonly, strong, nonatomic) NSManagedObjectContext *managedObjectContext;
@property (readonly, strong, nonatomic) NSManagedObjectModel *managedObjectModel;
@property (readonly, strong, nonatomic) NSPersistentStoreCoordinator *persistentStoreCoordinator;


@property (nonatomic, assign) BOOL connexionOK;

- (void)saveContext;
- (NSURL *)applicationDocumentsDirectory;



@end

